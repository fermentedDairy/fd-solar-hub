package org.fermented.dairy.common.rest.exception

sealed class RestException(message: String): RuntimeException(message) {
    class NotFoundException(message: String): RestException(message)
}