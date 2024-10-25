package org.fermented.dairy.boundary

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.mqtt.annotation.Topic
import io.micronaut.mqtt.annotation.v5.MqttPublisher
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import jakarta.inject.Inject
import org.fermented.dairy.entity.SolarDataRepository

@Suppress("unused", "unused", "unused")
@MicronautTest
class MqttConsumerTest (
    @Inject var publisher: TestPublisher,
    @Inject var solarDataRepository: SolarDataRepository
) : StringSpec({

    "given a test topic, consume message and persist" {
        publisher.publish("test/topic", "12345".toByteArray())
        val result1 = solarDataRepository.getSolarDataByTopicLatest("test/topic")
        result1 shouldNotBe null
        result1.should{ result1.isPresent() }
        result1.get().value shouldBe "12345"

        publisher.publish("test/topic", "54321".toByteArray())
        val result2 = solarDataRepository.getSolarDataByTopicLatest("test/topic")
        result2 shouldNotBe null
        result2.should{ result1.isPresent() }
        result2.get().value shouldBe "54321"
    }

})

@MqttPublisher
interface TestPublisher {
    fun publish(@Topic topic: String, message: ByteArray)
}
