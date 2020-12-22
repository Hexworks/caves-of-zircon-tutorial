package com.example.cavesofzircon.blocks

import com.example.cavesofzircon.builders.GameTileRepository.FLOOR
import com.example.cavesofzircon.builders.GameTileRepository.PLAYER
import com.example.cavesofzircon.builders.GameTileRepository.WALL
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.tile
import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BaseBlock

class GameBlock(
    private var defaultTile: Tile = FLOOR,
    private val currentEntities: MutableList<GameEntity<EntityType>> = mutableListOf(),  // 1
) : BaseBlock<Tile>(
    emptyTile = Tile.empty(),
    tiles = persistentMapOf(BlockTileType.CONTENT to defaultTile)
) {

    val isFloor: Boolean
        get() = defaultTile == FLOOR

    val isWall: Boolean
        get() = defaultTile == WALL

    val isEmptyFloor: Boolean                                       // 2
        get() = currentEntities.isEmpty()

    val entities: Iterable<GameEntity<EntityType>>                  // 3
        get() = currentEntities.toList()

    fun addEntity(entity: GameEntity<EntityType>) {                 // 4
        currentEntities.add(entity)
        updateContent()
    }

    fun removeEntity(entity: GameEntity<EntityType>) {              // 5
        currentEntities.remove(entity)
        updateContent()
    }

    private fun updateContent() {                                   // 6
        val entityTiles = currentEntities.map { it.tile }
        content = when {
            entityTiles.contains(PLAYER) -> PLAYER                  // 7
            entityTiles.isNotEmpty() -> entityTiles.first()         // 8
            else -> defaultTile                                     // 9
        }
    }
}
