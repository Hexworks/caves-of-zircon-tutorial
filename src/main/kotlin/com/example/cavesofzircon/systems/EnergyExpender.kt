package com.example.cavesofzircon.systems

import com.example.cavesofzircon.attributes.EnergyLevel
import com.example.cavesofzircon.attributes.types.EnergyUser
import com.example.cavesofzircon.attributes.types.energyLevel
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.whenTypeIs
import com.example.cavesofzircon.messages.Destroy
import com.example.cavesofzircon.messages.Expend
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseActor
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import kotlin.reflect.KClass

object EnergyExpender : BaseActor<GameContext, Expend>(Expend::class, EnergyLevel::class) {

    override val messageType: KClass<Expend> = Expend::class

    override suspend fun receive(message: Expend): Response {
        val (context, entity, energy) = message
        entity.energyLevel.currentEnergy -= energy              // 1
        checkStarvation(context, entity, entity.energyLevel)    // 2
        return Consumed
    }

    override suspend fun update(entity: Entity<EntityType, GameContext>, context: GameContext): Boolean {
        entity.whenTypeIs<EnergyUser> {                         // 3
            entity.receiveMessage(
                Expend(                                         // 4
                    context = context,
                    source = it,
                    energy = 2
                )
            )
        }
        return true
    }

    private suspend fun checkStarvation(
        context: GameContext,
        entity: GameEntity<EntityType>,
        energyLevel: EnergyLevel
    ) {
        if (energyLevel.currentEnergy <= 0) {                   // 5
            entity.receiveMessage(
                Destroy(                                        // 6
                    context = context,
                    source = entity,
                    target = entity,
                    cause = "because of starvation"
                )
            )
        }
    }
}
