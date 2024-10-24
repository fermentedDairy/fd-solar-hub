package org.fermented.dairy.boundary.rest

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.fermented.dairy.entity.repository.SolarDataRepository

@Controller("/fd-solar-hub")
class FdSolarHubController {

    @Get(uri = "/", produces = ["text/plain"])
    fun index(): String {
        return "Example Response"
    }
}