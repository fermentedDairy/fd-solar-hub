package org.fermented.dairy.entity

fun toRTO(solarData: SolarData): SolarDataRTO {
    return SolarDataRTO(
        time = solarData.time.toZonedDateTime(),
        topic = solarData.topic,
        value = solarData.value
    )
}