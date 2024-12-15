package org.fermented.dairy.automation.boundary.mqtt

import io.micronaut.mqtt.annotation.MqttSubscriber
import io.micronaut.mqtt.annotation.Topic
import io.github.oshai.kotlinlogging.KotlinLogging
import org.fermented.dairy.automation.entity.SolarData
import org.fermented.dairy.automation.entity.SolarDataRepository
import org.fermented.dairy.automation.entity.toDTO
import org.fermented.dairy.automation.service.AutomationProvider
import org.fermented.dairy.common.createV7UUID
import java.time.OffsetDateTime

@MqttSubscriber
class MqttConsumer (
    private val solarDataRepository: SolarDataRepository,
    private val automationProvider: AutomationProvider,
    ){

    @Topic("#")
    fun consume(@Topic topic: String, message: String) {
        logger.debug { "Topic: $topic, Message: $message" }
        val solarData = SolarData(createV7UUID(), OffsetDateTime.now(), topic, message)
        solarDataRepository.save(solarData)
        automationProvider.process(solarData.toDTO())
    }

    companion object {
        private val logger = KotlinLogging.logger(MqttConsumer::class.java.canonicalName)
    }

}