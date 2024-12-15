package org.fermented.dairy.automation.boundary.mqtt

interface MqttProducer {

    fun publishMaxChargeCurrentSet(message: ByteArray)

    companion object {
        const val MAX_CHARGE_CURRENT_SET = "solar_assistant/inverter_1/max_charge_current/set"
    }
}