package com.example.cavesofzircon.systems

import com.example.cavesofzircon.extensions.position
import com.example.cavesofzircon.extensions.sameLevelNeighborsShuffled
import com.example.cavesofzircon.messages.MoveTo
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType

object Wanderer : BaseBehavior<GameContext>() {

    override suspend fun update(entity: Entity<EntityType, GameContext>, context: GameContext): Boolean {
        val pos = entity.position
        if (pos.isUnknown.not()) {      // 1
            entity.receiveMessage(      // 2
                MoveTo(
                    context = context,
                    source = entity,
                    position = pos.sameLevelNeighborsShuffled().first() // 3
                )
            )
            return true
        }
        return false
    }
}
