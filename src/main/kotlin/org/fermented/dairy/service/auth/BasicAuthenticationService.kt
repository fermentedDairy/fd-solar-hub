package org.fermented.dairy.service.auth

import jakarta.inject.Singleton
import org.fermented.dairy.entity.auth.AuthInfo
import org.fermented.dairy.entity.auth.AuthType

@Singleton
class BasicAuthenticationService : AuthenticationService{
    override fun canProcess(authType: AuthType): Boolean {
        return authType == AuthType.BASIC
    }

    override fun authenticate(authInfo: AuthInfo, password: String) {
        TODO("Not yet implemented")
    }
}