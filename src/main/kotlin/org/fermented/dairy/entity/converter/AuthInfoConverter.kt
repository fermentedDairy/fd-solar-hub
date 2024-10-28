package org.fermented.dairy.entity.converter

import io.micronaut.core.convert.ConversionContext
import io.micronaut.data .model.runtime.convert.AttributeConverter
import jakarta.inject.Singleton
import org.fermented.dairy.entity.auth.AuthInfo

@Singleton
class AuthInfoConverter: AttributeConverter<AuthInfo, String> {

    override fun convertToPersistedValue(entityValue: AuthInfo?, context: ConversionContext?): String {
        TODO("Not yet implemented")
    }

    override fun convertToEntityValue(persistedValue: String?, context: ConversionContext?): AuthInfo {
        TODO("Not yet implemented")
    }
}