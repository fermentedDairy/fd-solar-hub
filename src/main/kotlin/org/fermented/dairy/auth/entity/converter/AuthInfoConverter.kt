package org.fermented.dairy.auth.entity.converter

import io.micronaut.core.convert.ConversionContext
import io.micronaut.data .model.runtime.convert.AttributeConverter
import io.micronaut.serde.ObjectMapper
import jakarta.inject.Singleton
import org.fermented.dairy.auth.entity.AuthInfo

@Singleton
class AuthInfoConverter(private val objectMapper: ObjectMapper): AttributeConverter<AuthInfo, String> {

    override fun convertToPersistedValue(entityValue: AuthInfo?, context: ConversionContext?): String =
        objectMapper.writeValueAsString(entityValue)

    override fun convertToEntityValue(persistedValue: String?, context: ConversionContext?): AuthInfo =
        objectMapper.readValue(persistedValue, AuthInfo::class.java)
}