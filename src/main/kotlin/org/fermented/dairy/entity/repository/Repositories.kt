package org.fermented.dairy.entity.repository

import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import org.fermented.dairy.entity.entities.Config
import org.fermented.dairy.entity.entities.SolarData
import java.util.*

@JdbcRepository(dialect = Dialect.POSTGRES)
interface ConfigRepository: CrudRepository<Config, String>

@JdbcRepository(dialect = Dialect.POSTGRES)
interface SolarDataRepository: CrudRepository<SolarData, UUID>