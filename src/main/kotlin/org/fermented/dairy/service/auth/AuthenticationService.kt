package org.fermented.dairy.service.auth

import org.fermented.dairy.entity.auth.AuthInfo
import org.fermented.dairy.entity.auth.AuthType

interface AuthenticationService {
    fun canProcess(authType: AuthType): Boolean

    fun authenticate(authInfo: AuthInfo, password: String)
}