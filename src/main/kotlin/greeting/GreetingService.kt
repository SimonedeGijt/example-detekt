package org.example.greeting

import org.springframework.stereotype.Service

@Service
class GreetingService {
    fun getGreetings(greeting: String) = if (
        greeting.startsWith("hello") &&
        !greeting.startsWith("bye") &&
        !greeting.endsWith("_") &&
        greeting.endsWith("!")
    ) println(greeting) else Unit
}
