package org.fermented.dairy.it

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.micronaut.mqtt.annotation.MqttSubscriber
import io.micronaut.mqtt.annotation.Topic
import io.micronaut.mqtt.annotation.v5.MqttPublisher
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.restassured.specification.RequestSpecification
import jakarta.inject.Inject
import org.fermented.dairy.automation.boundary.mqtt.MqttProducer.Companion.MAX_CHARGE_CURRENT_SET
import org.fermented.dairy.automation.service.ChargeCurrentAutomation.Companion.INVERTER_TEMPERATURE_STATE
import org.fermented.dairy.automation.service.ChargeCurrentAutomation.Companion.MAX_CHARGE_CURRENT_STATE
import org.hamcrest.CoreMatchers.equalTo
import kotlin.text.Charsets.UTF_8

@Suppress("unused")
@MicronautTest
class AutomationTest (
    @Inject var publisher: TestPublisher,
    @Inject var subscriber: TestSubscriber,
    private var spec: RequestSpecification
) : StringSpec({

    val testTopic = "test/topic"

    "given a test topic, consume message and persist" {
        publisher.publish(testTopic, "12345".toByteArray())
        Thread.sleep(100)
        validateTopicValue(spec, testTopic, "12345")


        publisher.publish(testTopic, "54321".toByteArray())
        validateTopicValue(spec, testTopic, "54321")

        validateTopicValues(spec, testTopic, "12345", "54321")
    }

    "given a the temperature/state topic, consume message and verify message published to temperature/state" {
        publisher.publish(INVERTER_TEMPERATURE_STATE, "46.5".toByteArray())
        validateTopicValue(spec, INVERTER_TEMPERATURE_STATE, "46.5")

        subscriber.valueMap[MAX_CHARGE_CURRENT_SET] shouldBe "40"
    }

    "given a the max_charge_current/state topic, consume message and verify message published to max_charge_current/set" {
        publisher.publish(INVERTER_TEMPERATURE_STATE, "46.5".toByteArray())

        subscriber.valueMap[MAX_CHARGE_CURRENT_SET] shouldBe "40"

        publisher.publish(MAX_CHARGE_CURRENT_STATE, "70".toByteArray())

        validateTopicValue(spec, MAX_CHARGE_CURRENT_STATE, "46.5")
        subscriber.valueMap[MAX_CHARGE_CURRENT_SET] shouldBe "40"
    }

})

private fun validateTopicValue(spec: RequestSpecification, topic: String, value: String) {

    spec.basePath("/fd-solar-hub/solar-data/{topic}/latest").pathParams("topic", topic)
        .get().then()
            .log().all()
            .statusCode(200)
            .body("topic", equalTo(topic))
            .body("value", equalTo(value))
}

private fun validateTopicValues(spec: RequestSpecification, topic: String, vararg values: String) {

    val response = spec.basePath("/fd-solar-hub/solar-data/{topic}").pathParams("topic", topic)
        .get().then()
        .log().all()
        .statusCode(200)

    for (i in values.indices) {
        response
            .body("[$i].topic", equalTo(topic))
            .body("[$i].value", equalTo(values[i]))
    }
}


@MqttPublisher
interface TestPublisher {
    fun publish(@Topic topic: String, message: ByteArray)
}

@MqttSubscriber
class TestSubscriber() {

    val valueMap: MutableMap<String, String>  = mutableMapOf()

    fun receive(@Topic topic: String, message: ByteArray) {
        valueMap[topic] = message.toString(UTF_8)
    }
}