package org.fermented.dairy.auth.service

import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationFailureReason
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider
import jakarta.inject.Singleton

@Singleton
class AuthenticationProvider<B>: HttpRequestAuthenticationProvider<B> {

    override fun authenticate(
        requestContext: HttpRequest<B>?,
        authenticationRequest: AuthenticationRequest<String, String>?
    ): AuthenticationResponse =
        authenticationRequest ?. let { AuthenticationResponse.success(authenticationRequest.identity) }
            ?: AuthenticationResponse.failure(AuthenticationFailureReason.UNKNOWN)
}
