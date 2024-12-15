package org.fermented.dairy.it

import io.kotest.core.spec.style.StringSpec
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.restassured.specification.RequestSpecification

@MicronautTest
class UserTest(
    private var spec: RequestSpecification
)  : StringSpec({

    "User request to User (self) get endpoint with incorrect auth details should fail" {
        spec.basePath("/fd-solar-hub/user/self").auth().basic("user", "badPassword")
            .get()
            .then()
            .log().all()
            .statusCode(401)
    }
})