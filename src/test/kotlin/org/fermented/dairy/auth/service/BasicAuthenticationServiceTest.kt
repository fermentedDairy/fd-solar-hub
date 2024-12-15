package org.fermented.dairy.auth.service

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.fermented.dairy.auth.entity.AuthInfo
import org.fermented.dairy.auth.entity.AuthRequest
import org.fermented.dairy.auth.entity.AuthType

class BasicAuthenticationServiceTest : StringSpec({

    "given a password, calculate hash and then check password against hash" {
        val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val authService = BasicAuthenticationService()
        val testPassword1 = "testPassword1"
        val testPassword2 = "testPassword2"
        val testPasswordRandom = List(20) { alphabet.random() }.joinToString("")

        validatePasswordHashing(testPassword1, authService)
        validatePasswordHashing(testPassword2, authService)
        validatePasswordHashing(testPasswordRandom, authService)
    }
})

fun validatePasswordHashing(testPassword: String, authService: BasicAuthenticationService) {

    val authRequest = AuthRequest.BasicAuthRequest(testPassword, false);
    authService.canProcess(AuthType.BASIC) shouldBe true
    val authInfo = authService.buildAuthInfo(authRequest);

    authInfo.shouldBeInstanceOf<AuthInfo.BasicAuthInfo>()

    authInfo.resetOnLogin shouldBe false

    authService.authenticate(authInfo, testPassword) shouldBe true

}
