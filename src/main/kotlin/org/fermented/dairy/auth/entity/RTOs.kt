package org.fermented.dairy.auth.entity

import io.micronaut.serde.annotation.Serdeable


@Serdeable
data class UserRTO (val time: String,
                    val roles: List<String>)