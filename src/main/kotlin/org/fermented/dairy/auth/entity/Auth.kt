package org.fermented.dairy.auth.entity

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
        data class BasicAuthInfo(val passwordHash: String, val salt: ByteArray, val resetOnLogin: Boolean) : AuthInfo()
    }

sealed class AuthRequest {
        data class BasicAuthRequest(val password: String, val resetOnLogin: Boolean): AuthRequest()
}

enum class AuthType(val type: String) {
    BASIC(type = "basic")
}