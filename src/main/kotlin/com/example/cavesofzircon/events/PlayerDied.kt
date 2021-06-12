package com.example.cavesofzircon.events

import org.hexworks.cobalt.events.api.Event

data class PlayerDied(
        val cause: String,
        override val emitter: Any
) : Event