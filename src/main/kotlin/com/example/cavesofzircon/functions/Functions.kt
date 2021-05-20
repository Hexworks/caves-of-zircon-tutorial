package com.example.cavesofzircon.functions

import com.example.cavesofzircon.events.GameLogEvent
import org.hexworks.zircon.internal.Zircon

fun logGameEvent(text: String, emitter: Any) {                    // 1
    Zircon.eventBus.publish(GameLogEvent(text, emitter))          // 2
}
