package com.example.cavesofzircon.builders

import com.example.cavesofzircon.attributes.EntityPosition
import com.example.cavesofzircon.attributes.EntityTile
import com.example.cavesofzircon.attributes.types.Player
import com.example.cavesofzircon.builders.GameTileRepository.PLAYER
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEntityOfType

fun <T : EntityType> newGameEntityOfType(
    type: T,
    init: EntityBuilder<T, GameContext>.() -> Unit
) = newEntityOfType(type, init)                          // 1

object EntityFactory {                                   // 2

    fun newPlayer() = newGameEntityOfType(Player) {      // 3
        attributes(EntityPosition(), EntityTile(PLAYER)) // 4
        behaviors()
        facets()
    }
}
