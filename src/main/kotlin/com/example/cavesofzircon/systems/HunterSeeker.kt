package com.example.cavesofzircon.systems

import com.example.cavesofzircon.extensions.position
import com.example.cavesofzircon.messages.MoveTo
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType

object HunterSeeker : BaseBehavior<GameContext>() {
    override suspend fun update(entity: Entity<EntityType, GameContext>, context: GameContext): Boolean {
        val (world, _, _, player) = context
        var hunted = false
        val path = world.findPath(entity, player)
        if (path.isNotEmpty()) {                    // 1
            entity.receiveMessage(MoveTo(           // 2
                    context = context,
                    source = entity,
                    position = path.iterator().next().toPosition3D(player.position.z))
            )
            hunted = true
        }
        return hunted   // 3
    }
}