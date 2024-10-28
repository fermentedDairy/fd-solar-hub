package org.fermented.dairy.entity.auth

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.micronaut.serde.annotation.Serdeable

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    Type(value = AuthInfo.BasicAuthInfo::class, name = "basic")
)
sealed class AuthInfo {
        @Serdeable
        data class BasicAuthInfo(val username: String, val password: String, val salt: String) : AuthInfo()

    }


enum class AuthType(val type: String) {

    BASIC(type = "basic")
}