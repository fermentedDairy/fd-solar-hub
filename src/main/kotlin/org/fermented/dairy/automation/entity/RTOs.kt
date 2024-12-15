package org.fermented.dairy.automation.entity

import io.micronaut.serde.annotation.Serdeable
import java.time.ZonedDateTime

@Serdeable
data class SolarDataRTO (val time: ZonedDateTime,
                         val topic: String,
                         val value: String)
