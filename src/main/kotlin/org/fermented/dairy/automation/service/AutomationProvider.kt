package org.fermented.dairy.automation.service

import jakarta.inject.Singleton
import org.fermented.dairy.automation.entity.SolarDataDTO

@Singleton
class AutomationProvider(private val automations: List<Automation>) {

    fun process(data: SolarDataDTO) {
        automations.asSequence()
            .filter { automation -> automation.canProcess(data.topic) }
            .forEach { automation -> automation.process(
                name = data.topic,
                value = data.value)}
    }
}