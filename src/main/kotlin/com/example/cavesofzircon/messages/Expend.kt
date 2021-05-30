package com.example.cavesofzircon.messages

import com.example.cavesofzircon.attributes.types.EnergyUser
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.GameMessage
import com.example.cavesofzircon.world.GameContext

data class Expend(
    override val context: GameContext,
    override val source: GameEntity<EnergyUser>,
    val energy: Int
) : GameMessage
