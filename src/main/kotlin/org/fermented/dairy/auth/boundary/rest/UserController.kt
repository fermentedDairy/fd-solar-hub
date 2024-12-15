package org.fermented.dairy.auth.boundary.rest

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.fermented.dairy.auth.entity.UserRTO

@Controller("/users")
class UserController {

    @Get(uri = "/authenticated", produces = [MediaType.APPLICATION_JSON])
    fun getAuthenticatedUser(): UserRTO? {
        return null;
    }
}