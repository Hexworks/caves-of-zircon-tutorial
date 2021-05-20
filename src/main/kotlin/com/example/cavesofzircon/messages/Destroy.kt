package com.example.cavesofzircon.messages

import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.GameMessage
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType

data class Destroy(
    override val context: GameContext,
    override val source: GameEntity<EntityType>, // 1
    val target: GameEntity<EntityType>,          // 2
    val cause: String = "natural causes."
) : GameMessage                                  // 3
