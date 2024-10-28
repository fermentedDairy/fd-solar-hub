package org.fermented.dairy.boundary

import io.kotest.core.spec.style.StringSpec
import io.micronaut.mqtt.annotation.Topic
import io.micronaut.mqtt.annotation.v5.MqttPublisher
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.restassured.specification.RequestSpecification
import jakarta.inject.Inject
import org.hamcrest.CoreMatchers.equalTo

@Suppress("unused", "unused", "unused")
@MicronautTest
class MetricsTest (
    @Inject var publisher: TestPublisher,
    private var spec: RequestSpecification
) : StringSpec({

    val testTopic = "test/topic"

    "given a test topic, consume message and persist" {
        publisher.publish(testTopic, "12345".toByteArray())
        Thread.sleep(100)
        validateTopicValue(spec, testTopic, "12345")


        publisher.publish(testTopic, "54321".toByteArray())
        Thread.sleep(100)
        validateTopicValue(spec, testTopic, "54321")

        validateTopicValues(spec, testTopic, "12345", "54321")
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
