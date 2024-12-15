package org.fermented.dairy.automation.entity

fun SolarData.toRTO() = SolarDataRTO(
        time = time.toZonedDateTime(),
        topic = topic,
        value = value
    )

fun SolarData.toDTO() = SolarDataDTO(
    topic = topic,
    value = value
)