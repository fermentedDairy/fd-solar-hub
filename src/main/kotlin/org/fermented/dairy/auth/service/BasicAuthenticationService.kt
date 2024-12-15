package org.fermented.dairy.auth.service

import jakarta.inject.Singleton
import org.fermented.dairy.auth.entity.AuthInfo
import org.fermented.dairy.auth.entity.AuthType
import org.fermented.dairy.auth.entity.AuthRequest
import org.fermented.dairy.auth.service.exceptions.AuthRuntimeException
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


@Singleton
class BasicAuthenticationService : AuthenticationService {

    private val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")

    override fun canProcess(authType: AuthType): Boolean {
        return authType == AuthType.BASIC
    }

    override fun authenticate(authInfo: AuthInfo, password: String): Boolean {
        when (authInfo) {
            is AuthInfo.BasicAuthInfo -> {
                val hash: ByteArray = getHash(
                    password = password,
                    salt = authInfo.salt)
                return String(hash, StandardCharsets.UTF_8) == authInfo.passwordHash
            }
            else -> throw AuthRuntimeException{"Unsupported AuthInfo type: " + authInfo::class.qualifiedName}
        }

    }

    override fun buildAuthInfo(authRequest: AuthRequest): AuthInfo {
        when (authRequest) {
            is AuthRequest.BasicAuthRequest -> {
                val salt = ByteArray(16)
                SecureRandom().nextBytes(salt)

                val hash: ByteArray =  getHash(authRequest.password, salt)

                return AuthInfo.BasicAuthInfo(
                    passwordHash = String(hash, StandardCharsets.UTF_8),
                    salt = salt,
                    resetOnLogin = authRequest.resetOnLogin)
            }
            else -> throw AuthRuntimeException{"Unsupported AuthRequest type: " + authRequest::class.qualifiedName}
        }

    }

    private fun getHash(password: String, salt: ByteArray): ByteArray {
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt, 65536, 128)

        val hash: ByteArray = factory.generateSecret(spec).encoded
        return hash
    }
}