package org.fermented.dairy.common.rest.exception

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.http.annotation.Produces
import jakarta.inject.Singleton

@Produces
@Singleton
@Requires(classes = [RestException::class, ExceptionHandler::class])
class RestExceptionHandler : ExceptionHandler<RestException, HttpResponse<Any>> {

    override fun handle(request: HttpRequest<*>?, exception: RestException): HttpResponse<Any> = when(exception){
            is RestException.NotFoundException -> HttpResponse.notFound(exception.message)
        }
}