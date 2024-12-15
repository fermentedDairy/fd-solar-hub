package org.fermented.dairy.automation.entity

import io.micronaut.cache.annotation.CacheConfig
import io.micronaut.cache.annotation.Cacheable
import io.micronaut.data.annotation.Query
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import io.micronaut.serde.ObjectMapper
import java.util.*
import java.util.stream.Stream
import kotlin.jvm.optionals.getOrDefault
import kotlin.jvm.optionals.getOrNull

@JdbcRepository(dialect = Dialect.POSTGRES)
@CacheConfig("config")
abstract class ConfigRepository(private val objectMapper: ObjectMapper): CrudRepository<Config, String> {

    @Cacheable
    abstract fun getByName(name: String): Optional<Config>

    fun getChargeCurrentConfig() : ChargeCurrentConfig {
        return getByName("chargeCurrent").map {
            objectMapper.readValue(it.value, ChargeCurrentConfig::class.java)
        }.getOrDefault(ChargeCurrentConfig.DEFAULT)
    }
}

@JdbcRepository(dialect = Dialect.POSTGRES)
interface SolarDataRepository: CrudRepository<SolarData, UUID> {
    @Query("select * from solar_data where topic = :topic order by time desc limit 1")
    fun getSolarDataByTopicLatest(topic: String): Optional<SolarData>

    fun retrieveAll(): Stream<SolarData>

    fun retrieveByTopic(name: String): Stream<SolarData>
}
