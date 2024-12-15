package org.fermented.dairy.auth.service

import org.fermented.dairy.auth.entity.AuthInfo
import org.fermented.dairy.auth.entity.AuthType
import org.fermented.dairy.auth.entity.AuthRequest

interface AuthenticationService {
    fun canProcess(authType: AuthType): Boolean

    fun authenticate(authInfo: AuthInfo, password: String): Boolean

    fun buildAuthInfo(authRequest: AuthRequest): AuthInfo
}