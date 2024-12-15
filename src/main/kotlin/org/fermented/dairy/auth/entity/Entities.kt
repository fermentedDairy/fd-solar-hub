package org.fermented.dairy.auth.entity

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import java.util.*


@MappedEntity
data class User(
    @field:Id
    val id: UUID,
    val username: String,
    val authInfo: AuthInfo,
    val roles: List<String>
)