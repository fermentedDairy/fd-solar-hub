package org.fermented.dairy.entity

import io.micronaut.data.annotation.Query
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import java.util.*

@JdbcRepository(dialect = Dialect.POSTGRES)
interface ConfigRepository: CrudRepository<Config, String>

@JdbcRepository(dialect = Dialect.POSTGRES)
interface SolarDataRepository: CrudRepository<SolarData, UUID> {
    @Query("select * from solar_data where topic = :topic order by time desc limit 1")
    fun getSolarDataByTopicLatest(topic: String): Optional<SolarData>
}