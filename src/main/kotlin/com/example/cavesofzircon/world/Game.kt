package com.example.cavesofzircon.world

import com.example.cavesofzircon.GameConfig.GAME_COMPONENT_SIZE
import com.example.cavesofzircon.GameConfig.WORLD_SIZE
import com.example.cavesofzircon.builders.WorldBuilder
import org.hexworks.zircon.api.data.Size3D

class Game(val world: World) {

    companion object {

        fun create(
            worldSize: Size3D = WORLD_SIZE,
            visibleSize: Size3D = GAME_COMPONENT_SIZE
        ) = Game(
            WorldBuilder(worldSize)
                .makeCaves()
                .build(visibleSize)
        )
    }
}
