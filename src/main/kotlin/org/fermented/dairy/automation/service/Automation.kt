package org.fermented.dairy.automation.service

interface Automation {
    fun process(name: String, value: String )
    fun canProcess(name: String): Boolean
}

