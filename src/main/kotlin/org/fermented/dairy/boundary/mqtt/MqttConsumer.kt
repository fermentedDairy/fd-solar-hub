package org.fermented.dairy.boundary.mqtt

import io.micronaut.mqtt.annotation.MqttSubscriber
import io.micronaut.mqtt.annotation.Topic
import mu.KotlinLogging
import org.fermented.dairy.entity.entities.SolarData
import org.fermented.dairy.entity.repository.SolarDataRepository
import org.fermented.dairy.entity.util.createV7UUID
import java.time.OffsetDateTime

@MqttSubscriber
class MqttConsumer (val solarDataRepository: SolarDataRepository){
    private val logger = KotlinLogging.logger {}

    @Topic("#")
    fun consume(@Topic topic: String, message: String) {
        logger.info { "Topic: $topic, Message: $message" }
        val solarData = SolarData(createV7UUID(), OffsetDateTime.now(), topic, message)
        solarDataRepository.save(solarData)
    }

}