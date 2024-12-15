package org.fermented.dairy.automation.boundary.rest

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.transaction.annotation.Transactional
import jakarta.inject.Inject
import org.fermented.dairy.common.rest.exception.RestException
import org.fermented.dairy.automation.entity.SolarData
import org.fermented.dairy.automation.entity.SolarDataRTO
import org.fermented.dairy.automation.entity.SolarDataRepository
import org.fermented.dairy.automation.entity.toRTO

@Controller("/solar-data")
@Secured(SecurityRule.IS_ANONYMOUS)
open class SolarDataController (
    @Inject private val solarDataRepository: SolarDataRepository
){

    @Get(uri = "/", produces = ["application/json"])
    @Transactional
    open fun getAllMetrics(): List<SolarDataRTO> =
        solarDataRepository.retrieveAll().use {
            stream -> stream.map(SolarData::toRTO).toList()
        }

    @Get(uri = "/{metricName}", produces = ["application/json"])
    @Transactional
    open fun getMetricData(@PathVariable metricName: String): List<SolarDataRTO> =
        solarDataRepository.retrieveByTopic(metricName).use {
                stream -> stream.map(SolarData::toRTO).toList()
        }

    @Get(uri = "/{metricName}/latest", produces = [MediaType.APPLICATION_JSON])
    fun getMetricDataLatest(@PathVariable metricName: String): SolarDataRTO =
        solarDataRepository.getSolarDataByTopicLatest(metricName)
            .map(SolarData::toRTO)
            .orElseThrow{ RestException.NotFoundException("metric: $metricName not found")}
}