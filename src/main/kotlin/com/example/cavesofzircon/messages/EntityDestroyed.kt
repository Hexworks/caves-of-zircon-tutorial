package com.example.cavesofzircon.messages

import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.GameMessage
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType

data class EntityDestroyed(
        override val context: GameContext,
        override val source: GameEntity<EntityType>,
        val destroyer: GameEntity<EntityType>
) : GameMessage