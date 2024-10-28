package org.fermented.dairy.entity

import java.time.OffsetDateTime
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import org.fermented.dairy.entity.auth.AuthInfo
import java.util.UUID

@MappedEntity
data class User(
    @field:Id
    val id: UUID,
    val username: String,
    val authInfo: AuthInfo,
    val roles: List<String>
)

@MappedEntity
data class SolarData(
    @field:Id
    val id: UUID,
    val time: OffsetDateTime,
    val topic: String,
    val value: String)


@MappedEntity
data class Config(@field:Id
                  val name: String,
                  val value: String)
