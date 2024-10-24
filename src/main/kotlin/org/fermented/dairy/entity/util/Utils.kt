package org.fermented.dairy.entity.util

import java.util.UUID
import com.fasterxml.uuid.Generators

fun createV7UUID(): UUID {
    return Generators.timeBasedGenerator().generate()
}