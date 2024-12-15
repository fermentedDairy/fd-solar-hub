package org.fermented.dairy.automation.entity

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class SolarDataDTO (val topic: String,
                         val value: String)