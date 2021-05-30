package com.example.cavesofzircon.messages

import com.example.cavesofzircon.attributes.types.EnergyUser
import com.example.cavesofzircon.attributes.types.Food
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.world.GameContext

data class Eat(
    override val context: GameContext,
    override val source: GameEntity<EnergyUser>,
    override val target: GameEntity<Food>
) : EntityAction<EnergyUser, Food>
