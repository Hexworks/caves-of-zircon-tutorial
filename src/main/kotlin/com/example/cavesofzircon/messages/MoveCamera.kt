package com.example.cavesofzircon.messages

import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.GameMessage
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.Position3D

data class MoveCamera(
        override val context: GameContext,
        override val source: GameEntity<EntityType>,
        val previousPosition: Position3D
) : GameMessage