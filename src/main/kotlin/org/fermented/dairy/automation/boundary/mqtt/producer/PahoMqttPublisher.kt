package org.fermented.dairy.automation.boundary.mqtt.producer

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.inject.Singleton
import org.eclipse.paho.mqttv5.common.MqttMessage
import org.fermented.dairy.automation.boundary.mqtt.MqttProducer
import org.fermented.dairy.automation.boundary.mqtt.MqttProducer.Companion.MAX_CHARGE_CURRENT_SET
import kotlin.text.Charsets.UTF_8

@Singleton
class PahoMqttPublisher(private val pahoClientPool: PahoClientPool): MqttProducer {

    override fun publishMaxChargeCurrentSet(message: ByteArray) {
        logger.info { "Publish max charge current set to ${message.toString(UTF_8)}" }
        pahoClientPool.borrowObject().use {
            connection ->
                val mqttMessage = MqttMessage(message)
                val conn = connection.borrow()
                conn.connect()
                conn.publish(MAX_CHARGE_CURRENT_SET, mqttMessage)
        }
    }

    companion object {
        private val logger = KotlinLogging.logger(PahoMqttPublisher::class.java.canonicalName)
    }
}
