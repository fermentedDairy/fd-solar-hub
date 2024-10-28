package org.fermented.dairy.boundary.rest.exception

sealed class RestException(message: String): RuntimeException(message)

class NotFoundException(message: String): RestException(message)