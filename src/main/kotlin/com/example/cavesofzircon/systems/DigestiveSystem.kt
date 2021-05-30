package com.example.cavesofzircon.systems

import com.example.cavesofzircon.attributes.EnergyLevel
import com.example.cavesofzircon.attributes.types.energy
import com.example.cavesofzircon.attributes.types.energyLevel
import com.example.cavesofzircon.extensions.isPlayer
import com.example.cavesofzircon.functions.logGameEvent
import com.example.cavesofzircon.messages.Eat
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object DigestiveSystem : BaseFacet<GameContext, Eat>(Eat::class, EnergyLevel::class) {
    override suspend fun receive(message: Eat): Response {
        val (_, entity, food) = message
        entity.energyLevel.currentEnergy += food.energy
        val verb = if (entity.isPlayer) {
            "You eat"
        } else "The $entity eats"
        logGameEvent("$verb the $food.", DigestiveSystem)
        return Consumed
    }
}
