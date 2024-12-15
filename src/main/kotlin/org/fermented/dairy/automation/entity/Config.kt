package org.fermented.dairy.automation.entity

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ChargeCurrentConfig(
    val t1: Double,
    val t2:Double,
    val green: Int,
    val yellow: Int,
    val red: Int) {

    fun getSettingValue(temperature: Double): Int {
        if (temperature <= t1) {
            return green
        }
        if (temperature <= t2) {
            return yellow
        }

        return red
    }

    companion object {
        val DEFAULT = ChargeCurrentConfig(t1 = 45.0, t2 = 50.0, green = 70, yellow = 40, red = 30)
    }
}