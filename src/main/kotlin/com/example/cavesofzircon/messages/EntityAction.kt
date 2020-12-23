package com.example.cavesofzircon.messages

import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.GameMessage
import org.hexworks.amethyst.api.entity.EntityType

interface EntityAction<S : EntityType, T : EntityType> : GameMessage { // 1

    val target: GameEntity<T>               // 2

    operator fun component1() = context     // 3
    operator fun component2() = source
    operator fun component3() = target
}