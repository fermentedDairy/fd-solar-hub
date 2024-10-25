package org.fermented.dairy.boundary.rest

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/fd-solar-hub")
class FdSolarHubController {

    @Get(uri = "/", produces = ["text/plain"])
    fun index(): String {
        return "Example Response"
    }
}