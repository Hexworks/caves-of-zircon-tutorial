package com.example.cavesofzircon.systems

import com.example.cavesofzircon.builders.GameTileRepository
import com.example.cavesofzircon.extensions.position
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.Position3D

object FogOfWar : BaseBehavior<GameContext>() {
    override suspend fun update(entity: Entity<EntityType, GameContext>, context: GameContext): Boolean {
        val (world, _, _, player) = context
        world.findVisiblePositionsFor(player).forEach { pos ->
            world.fetchBlockAt(
                Position3D.create(
                    x = pos.x,
                    y = pos.y,
                    z = player.position.z
                )
            ).map { block ->
                block.top = GameTileRepository.EMPTY
            }
        }
        return true
    }
}
