package com.example.cavesofzircon.world

import com.example.cavesofzircon.attributes.types.Player
import com.example.cavesofzircon.extensions.GameEntity

class Game(
    val world: World,
    val player: GameEntity<Player>
) {

    companion object {

        fun create(
            player: GameEntity<Player>,
            world: World
        ) = Game(
            world = world,
            player = player
        )
    }
}
