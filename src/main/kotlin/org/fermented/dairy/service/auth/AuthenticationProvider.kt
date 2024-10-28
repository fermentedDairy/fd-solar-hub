package org.fermented.dairy.service.auth

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
    ): AuthenticationResponse {
        return if (authenticationRequest == null)
            AuthenticationResponse.failure(AuthenticationFailureReason.UNKNOWN)
        else AuthenticationResponse.success(authenticationRequest.identity)
    }
}
