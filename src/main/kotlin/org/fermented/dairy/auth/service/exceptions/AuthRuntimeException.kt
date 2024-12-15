package org.fermented.dairy.auth.service.exceptions

class AuthRuntimeException(message: () -> String?) : RuntimeException(message.invoke()) {
}