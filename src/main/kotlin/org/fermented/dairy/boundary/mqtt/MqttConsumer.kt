package org.fermented.dairy.boundary.mqtt

import io.micronaut.mqtt.annotation.MqttSubscriber
import io.micronaut.mqtt.annotation.Topic
import io.github.oshai.kotlinlogging.KotlinLogging
import org.fermented.dairy.entity.SolarData
import org.fermented.dairy.entity.SolarDataRepository
import org.fermented.dairy.entity.util.createV7UUID
import java.time.OffsetDateTime

@MqttSubscriber
class MqttConsumer (private val solarDataRepository: SolarDataRepository){
    private val logger = KotlinLogging.logger {}

    @Topic("#")
    fun consume(@Topic topic: String, message: String) {
        logger.debug { "Topic: $topic, Message: $message" }
        val solarData = SolarData(createV7UUID(), OffsetDateTime.now(), topic, message)
        solarDataRepository.save(solarData)
    }

}