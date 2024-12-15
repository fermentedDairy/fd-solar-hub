package org.fermented.dairy.automation.service

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.inject.Singleton
import org.fermented.dairy.automation.boundary.mqtt.MqttProducer
import org.fermented.dairy.automation.entity.ConfigRepository
import java.util.concurrent.atomic.AtomicInteger

@Singleton
class ChargeCurrentAutomation(
    private val configRepository: ConfigRepository,
    private val mqttProducer: MqttProducer
): Automation {

    private val logger = KotlinLogging.logger(this::class.java.canonicalName)

    private var desiredCurrent: AtomicInteger = AtomicInteger(0)

    override fun process(name: String, value: String) {
        if (INVERTER_TEMPERATURE_STATE == name)
        {
            logger.info { "processing message for $MAX_CHARGE_CURRENT_STATE" }
            val requiredCurrent = configRepository.getChargeCurrentConfig()
                .getSettingValue(value.toDouble());
            logger.info { "requiredCurrent: $requiredCurrent" }
            if (requiredCurrent != desiredCurrent.get()) {
                desiredCurrent.set(requiredCurrent);
                logger.info { "desiredCurrent set to $requiredCurrent" }
                mqttProducer.publishMaxChargeCurrentSet(requiredCurrent.toString().toByteArray(Charsets.UTF_8))
                logger.info { "Sending message for $INVERTER_TEMPERATURE_STATE" }
            }
        } else if (MAX_CHARGE_CURRENT_STATE == name && desiredCurrent.get() != 0) {
            if (desiredCurrent.get() != Integer.parseInt(value)) {
                mqttProducer.publishMaxChargeCurrentSet(desiredCurrent.get().toString().toByteArray(Charsets.UTF_8))
                logger.info { "Sending message for $MAX_CHARGE_CURRENT_STATE" }
            }
        }
        return
    }

    override fun canProcess(name: String): Boolean = TOPICS.contains(name)

    companion object {
        const val INVERTER_TEMPERATURE_STATE = "solar_assistant/inverter_1/temperature/state"
        const val MAX_CHARGE_CURRENT_STATE = "solar_assistant/inverter_1/max_charge_current/state"
        val TOPICS = setOf(INVERTER_TEMPERATURE_STATE, MAX_CHARGE_CURRENT_STATE)
    }
}