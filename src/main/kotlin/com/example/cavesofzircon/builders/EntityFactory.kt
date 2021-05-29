package com.example.cavesofzircon.builders

import com.example.cavesofzircon.attributes.CombatStats
import com.example.cavesofzircon.attributes.EntityActions
import com.example.cavesofzircon.attributes.EntityPosition
import com.example.cavesofzircon.attributes.EntityTile
import com.example.cavesofzircon.attributes.FungusSpread
import com.example.cavesofzircon.attributes.Inventory
import com.example.cavesofzircon.attributes.ItemIcon
import com.example.cavesofzircon.attributes.Vision
import com.example.cavesofzircon.attributes.VisionBlocker
import com.example.cavesofzircon.attributes.flags.BlockOccupier
import com.example.cavesofzircon.attributes.types.Bat
import com.example.cavesofzircon.attributes.types.FOW
import com.example.cavesofzircon.attributes.types.Fungus
import com.example.cavesofzircon.attributes.types.Player
import com.example.cavesofzircon.attributes.types.StairsDown
import com.example.cavesofzircon.attributes.types.StairsUp
import com.example.cavesofzircon.attributes.types.Wall
import com.example.cavesofzircon.attributes.types.Zircon
import com.example.cavesofzircon.messages.Attack
import com.example.cavesofzircon.messages.Dig
import com.example.cavesofzircon.systems.Attackable
import com.example.cavesofzircon.systems.CameraMover
import com.example.cavesofzircon.systems.Destructible
import com.example.cavesofzircon.systems.Diggable
import com.example.cavesofzircon.systems.FogOfWar
import com.example.cavesofzircon.systems.FungusGrowth
import com.example.cavesofzircon.systems.InputReceiver
import com.example.cavesofzircon.systems.InventoryInspector
import com.example.cavesofzircon.systems.ItemDropper
import com.example.cavesofzircon.systems.ItemPicker
import com.example.cavesofzircon.systems.Movable
import com.example.cavesofzircon.systems.StairClimber
import com.example.cavesofzircon.systems.StairDescender
import com.example.cavesofzircon.systems.Wanderer
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEntityOfType
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.data.Tile

fun <T : EntityType> newGameEntityOfType(
    type: T,
    init: EntityBuilder<T, GameContext>.() -> Unit
) = newEntityOfType(type, init)

object EntityFactory {

    fun newPlayer() = newGameEntityOfType(Player) {
        attributes(
            Vision(9),
            EntityPosition(),
            BlockOccupier,
            CombatStats.create(
                maxHp = 100,
                attackValue = 10,
                defenseValue = 5
            ),
            EntityTile(GameTileRepository.PLAYER),
            EntityActions(Dig::class, Attack::class),
            Inventory(10)
        )
        behaviors(InputReceiver)
        facets(
            Movable,
            CameraMover,
            StairClimber,
            StairDescender,
            Attackable,
            Destructible,
            ItemPicker,
            InventoryInspector,
            ItemDropper
        )
    }

    fun newWall() = newGameEntityOfType(Wall) {
        attributes(
            EntityPosition(),
            BlockOccupier,
            EntityTile(GameTileRepository.WALL),
            VisionBlocker
        )
        facets(Diggable)
    }

    fun newFungus(fungusSpread: FungusSpread = FungusSpread()) = newGameEntityOfType(Fungus) {
        attributes(
            BlockOccupier,
            EntityPosition(),
            EntityTile(GameTileRepository.FUNGUS),
            fungusSpread,
            CombatStats.create(
                maxHp = 10,
                attackValue = 0,
                defenseValue = 0
            )
        )
        facets(Attackable, Destructible)
        behaviors(FungusGrowth)
    }

    fun newStairsDown() = newGameEntityOfType(StairsDown) {
        attributes(
            EntityTile(GameTileRepository.STAIRS_DOWN),
            EntityPosition()
        )
    }

    fun newStairsUp() = newGameEntityOfType(StairsUp) {
        attributes(
            EntityTile(GameTileRepository.STAIRS_UP),
            EntityPosition()
        )
    }

    fun newFogOfWar() = newGameEntityOfType(FOW) {
        behaviors(FogOfWar)
    }

    fun newBat() = newGameEntityOfType(Bat) {
        attributes(
            BlockOccupier,                      // 1
            EntityPosition(),
            EntityTile(GameTileRepository.BAT),
            CombatStats.create(                 // 2
                maxHp = 5,
                attackValue = 2,
                defenseValue = 1
            ),
            EntityActions(Attack::class)        // 3
        )
        facets(Movable, Attackable, Destructible)   // 4
        behaviors(Wanderer)                         // 5
    }

    fun newZircon() = newGameEntityOfType(Zircon) {
        attributes(
            ItemIcon(
                Tile.newBuilder()
                    .withName("white gem")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            ),
            EntityPosition(),
            EntityTile(GameTileRepository.ZIRCON)
        )
    }
}
