package org.fermented.dairy.boundary.rest

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.transaction.annotation.Transactional
import jakarta.inject.Inject
import org.fermented.dairy.boundary.rest.exception.NotFoundException
import org.fermented.dairy.entity.SolarDataRTO
import org.fermented.dairy.entity.SolarDataRepository
import org.fermented.dairy.entity.toRTO
import java.util.stream.Stream

@Controller("/solar-data")
@Secured(SecurityRule.IS_ANONYMOUS)
open class SolarDataController (
    @Inject private val solarDataRepository: SolarDataRepository
){

    @Get(uri = "/", produces = ["application/json"])
    @Transactional
    open fun getAllMetrics(): List<SolarDataRTO> {
        return Stream.of(solarDataRepository.retrieveAll()).flatMap { stream -> stream }
            .map(::toRTO).toList()
    }

    @Get(uri = "/{metricName}", produces = ["application/json"])
    @Transactional
    open fun getMetricData(@PathVariable metricName: String): List<SolarDataRTO> {
        return Stream.of(solarDataRepository.retrieveByTopic(metricName)).flatMap { stream -> stream }
            .map(::toRTO).toList()
    }

    @Get(uri = "/{metricName}/latest", produces = [MediaType.APPLICATION_JSON])
    fun getMetricDataLatest(@PathVariable metricName: String): SolarDataRTO {
        val result: SolarDataRTO = solarDataRepository.getSolarDataByTopicLatest(metricName).map {
            data -> toRTO(data)
        }.orElseThrow{ NotFoundException("metric: $metricName not found" ) }
        return result
    }
}