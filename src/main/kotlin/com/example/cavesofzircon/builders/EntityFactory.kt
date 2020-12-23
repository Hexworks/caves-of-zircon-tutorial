package com.example.cavesofzircon.builders

import com.example.cavesofzircon.attributes.EntityActions
import com.example.cavesofzircon.attributes.EntityPosition
import com.example.cavesofzircon.attributes.EntityTile
import com.example.cavesofzircon.attributes.flags.BlockOccupier
import com.example.cavesofzircon.attributes.types.Player
import com.example.cavesofzircon.attributes.types.Wall
import com.example.cavesofzircon.messages.Dig
import com.example.cavesofzircon.systems.CameraMover
import com.example.cavesofzircon.systems.Diggable
import com.example.cavesofzircon.systems.InputReceiver
import com.example.cavesofzircon.systems.Movable
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEntityOfType

fun <T : EntityType> newGameEntityOfType(
        type: T,
        init: EntityBuilder<T, GameContext>.() -> Unit
) = newEntityOfType(type, init)

object EntityFactory {

    fun newPlayer() = newGameEntityOfType(Player) {
        attributes(
                EntityPosition(),
                EntityTile(GameTileRepository.PLAYER),
                EntityActions(Dig::class)
        )
        behaviors(InputReceiver)
        facets(Movable, CameraMover)
    }

    fun newWall() = newGameEntityOfType(Wall) {
        attributes(
                EntityPosition(),
                BlockOccupier,
                EntityTile(GameTileRepository.WALL))
        facets(Diggable)
    }
}
