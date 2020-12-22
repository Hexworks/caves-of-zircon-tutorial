package com.example.cavesofzircon.world

import com.example.cavesofzircon.GameConfig
import com.example.cavesofzircon.GameConfig.LOG_AREA_HEIGHT
import com.example.cavesofzircon.GameConfig.SIDEBAR_WIDTH
import com.example.cavesofzircon.GameConfig.WINDOW_HEIGHT
import com.example.cavesofzircon.GameConfig.WINDOW_WIDTH
import com.example.cavesofzircon.GameConfig.WORLD_SIZE
import com.example.cavesofzircon.attributes.types.Player
import com.example.cavesofzircon.builders.EntityFactory
import com.example.cavesofzircon.builders.WorldBuilder
import com.example.cavesofzircon.extensions.GameEntity
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

class GameBuilder(val worldSize: Size3D) {          // 1

    private val visibleSize = Size3D.create(        // 2
        xLength = WINDOW_WIDTH - SIDEBAR_WIDTH,
        yLength = WINDOW_HEIGHT - LOG_AREA_HEIGHT,
        zLength = 1
    )

    val world = WorldBuilder(worldSize)             // 3
        .makeCaves()
        .build(visibleSize = visibleSize)

    fun buildGame(): Game {

        prepareWorld()

        val player = addPlayer()

        return Game.create(
            player = player,
            world = world
        )
    }

    private fun prepareWorld() = also {             // 4
        world.scrollUpBy(world.actualSize.zLength)
    }

    private fun addPlayer(): GameEntity<Player> {
        val player = EntityFactory.newPlayer()      // 5
        world.addAtEmptyPosition(
            player,                                 // 6
            offset = Position3D.create(0, 0, GameConfig.DUNGEON_LEVELS - 1), // 7
            size = world.visibleSize.copy(zLength = 0)
        )                                           // 8
        return player
    }

    companion object {

        fun create() = GameBuilder(
            worldSize = WORLD_SIZE
        ).buildGame()
    }
}
