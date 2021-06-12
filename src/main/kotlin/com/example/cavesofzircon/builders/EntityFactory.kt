package com.example.cavesofzircon.builders

import com.example.cavesofzircon.attributes.CombatStats
import com.example.cavesofzircon.attributes.EnergyLevel
import com.example.cavesofzircon.attributes.EntityActions
import com.example.cavesofzircon.attributes.EntityPosition
import com.example.cavesofzircon.attributes.EntityTile
import com.example.cavesofzircon.attributes.Equipment
import com.example.cavesofzircon.attributes.FungusSpread
import com.example.cavesofzircon.attributes.Inventory
import com.example.cavesofzircon.attributes.ItemCombatStats
import com.example.cavesofzircon.attributes.ItemIcon
import com.example.cavesofzircon.attributes.NutritionalValue
import com.example.cavesofzircon.attributes.Vision
import com.example.cavesofzircon.attributes.VisionBlocker
import com.example.cavesofzircon.attributes.flags.BlockOccupier
import com.example.cavesofzircon.attributes.types.Armor
import com.example.cavesofzircon.attributes.types.Bat
import com.example.cavesofzircon.attributes.types.BatMeat
import com.example.cavesofzircon.attributes.types.Club
import com.example.cavesofzircon.attributes.types.Dagger
import com.example.cavesofzircon.attributes.types.FOW
import com.example.cavesofzircon.attributes.types.Fungus
import com.example.cavesofzircon.attributes.types.HeavyArmor
import com.example.cavesofzircon.attributes.types.Jacket
import com.example.cavesofzircon.attributes.types.LightArmor
import com.example.cavesofzircon.attributes.types.MediumArmor
import com.example.cavesofzircon.attributes.types.Player
import com.example.cavesofzircon.attributes.types.Staff
import com.example.cavesofzircon.attributes.types.StairsDown
import com.example.cavesofzircon.attributes.types.StairsUp
import com.example.cavesofzircon.attributes.types.Sword
import com.example.cavesofzircon.attributes.types.Wall
import com.example.cavesofzircon.attributes.types.Weapon
import com.example.cavesofzircon.attributes.types.Zircon
import com.example.cavesofzircon.attributes.types.Zombie
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.messages.Attack
import com.example.cavesofzircon.messages.Dig
import com.example.cavesofzircon.systems.Attackable
import com.example.cavesofzircon.systems.CameraMover
import com.example.cavesofzircon.systems.Destructible
import com.example.cavesofzircon.systems.DigestiveSystem
import com.example.cavesofzircon.systems.Diggable
import com.example.cavesofzircon.systems.EnergyExpender
import com.example.cavesofzircon.systems.FogOfWar
import com.example.cavesofzircon.systems.FungusGrowth
import com.example.cavesofzircon.systems.HunterSeeker
import com.example.cavesofzircon.systems.InputReceiver
import com.example.cavesofzircon.systems.InventoryInspector
import com.example.cavesofzircon.systems.ItemDropper
import com.example.cavesofzircon.systems.ItemPicker
import com.example.cavesofzircon.systems.LootDropper
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
import kotlin.random.Random

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
                Inventory(10),
                EnergyLevel(1000, 1000),
                Equipment(
                        initialWeapon = newClub(),
                        initialArmor = newJacket()
                )
        )
        behaviors(InputReceiver, EnergyExpender)
        facets(
                Movable,
                CameraMover,
                StairClimber,
                StairDescender,
                Attackable,
                Destructible,
                ItemPicker,
                InventoryInspector,
                ItemDropper,
                EnergyExpender,
                DigestiveSystem
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
                BlockOccupier,
                EntityPosition(),
                EntityTile(GameTileRepository.BAT),
                CombatStats.create(
                        maxHp = 5,
                        attackValue = 2,
                        defenseValue = 1
                ),
                EntityActions(Attack::class),
                Inventory(1).apply {
                    addItem(newBatMeat())   // 1
                }
        )
        facets(Movable, Attackable, ItemDropper, LootDropper, Destructible)
        behaviors(Wanderer)
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

    fun newBatMeat() = newGameEntityOfType(BatMeat) {
        attributes(
                ItemIcon(
                        Tile.newBuilder()
                                .withName("Meatball")           // 1
                                .withTileset(GraphicalTilesetResources.nethack16x16())
                                .buildGraphicalTile()
                ),
                NutritionalValue(750),              // 2
                EntityPosition(),
                EntityTile(GameTileRepository.BAT_MEAT)
        )
    }

    fun newDagger() = newGameEntityOfType(Dagger) {
        attributes(ItemIcon(Tile.newBuilder()
                .withName("Dagger")
                .withTileset(GraphicalTilesetResources.nethack16x16())
                .buildGraphicalTile()),
                EntityPosition(),
                ItemCombatStats(
                        attackValue = 4,
                        combatItemType = "Weapon"),
                EntityTile(GameTileRepository.DAGGER))
    }

    fun newSword() = newGameEntityOfType(Sword) {
        attributes(ItemIcon(Tile.newBuilder()
                .withName("Short sword")
                .withTileset(GraphicalTilesetResources.nethack16x16())
                .buildGraphicalTile()),
                EntityPosition(),
                ItemCombatStats(
                        attackValue = 6,
                        combatItemType = "Weapon"),
                EntityTile(GameTileRepository.SWORD))
    }

    fun newStaff() = newGameEntityOfType(Staff) {
        attributes(ItemIcon(Tile.newBuilder()
                .withName("staff")
                .withTileset(GraphicalTilesetResources.nethack16x16())
                .buildGraphicalTile()),
                EntityPosition(),
                ItemCombatStats(
                        attackValue = 4,
                        defenseValue = 2,
                        combatItemType = "Weapon"),
                EntityTile(GameTileRepository.STAFF))
    }

    fun newLightArmor() = newGameEntityOfType(LightArmor) {
        attributes(ItemIcon(Tile.newBuilder()
                .withName("Leather armor")
                .withTileset(GraphicalTilesetResources.nethack16x16())
                .buildGraphicalTile()),
                EntityPosition(),
                ItemCombatStats(
                        defenseValue = 2,
                        combatItemType = "Armor"),
                EntityTile(GameTileRepository.LIGHT_ARMOR))
    }

    fun newMediumArmor() = newGameEntityOfType(MediumArmor) {
        attributes(ItemIcon(Tile.newBuilder()
                .withName("Chain mail")
                .withTileset(GraphicalTilesetResources.nethack16x16())
                .buildGraphicalTile()),
                EntityPosition(),
                ItemCombatStats(
                        defenseValue = 3,
                        combatItemType = "Armor"),
                EntityTile(GameTileRepository.MEDIUM_ARMOR))
    }

    fun newHeavyArmor() = newGameEntityOfType(HeavyArmor) {
        attributes(ItemIcon(Tile.newBuilder()
                .withName("Plate mail")
                .withTileset(GraphicalTilesetResources.nethack16x16())
                .buildGraphicalTile()),
                EntityPosition(),
                ItemCombatStats(
                        defenseValue = 4,
                        combatItemType = "Armor"),
                EntityTile(GameTileRepository.HEAVY_ARMOR))
    }

    fun newClub() = newGameEntityOfType(Club) {
        attributes(ItemCombatStats(combatItemType = "Weapon"),
                EntityTile(GameTileRepository.CLUB),
                EntityPosition(),
                ItemIcon(Tile.newBuilder()
                        .withName("Club")
                        .withTileset(GraphicalTilesetResources.nethack16x16())
                        .buildGraphicalTile()))
    }

    fun newJacket() = newGameEntityOfType(Jacket) {
        attributes(ItemCombatStats(combatItemType = "Armor"),
                EntityTile(GameTileRepository.JACKET),
                EntityPosition(),
                ItemIcon(Tile.newBuilder()
                        .withName("Leather jacket")
                        .withTileset(GraphicalTilesetResources.nethack16x16())
                        .buildGraphicalTile()))
    }

    fun newRandomWeapon(): GameEntity<Weapon> = when (Random.nextInt(3)) {
        0 -> newDagger()
        1 -> newSword()
        else -> newStaff()
    }

    fun newRandomArmor(): GameEntity<Armor> = when (Random.nextInt(3)) {
        0 -> newLightArmor()
        1 -> newMediumArmor()
        else -> newHeavyArmor()
    }

    fun newZombie() = newGameEntityOfType(Zombie) {
        attributes(BlockOccupier,
                EntityPosition(),
                EntityTile(GameTileRepository.ZOMBIE),
                Vision(10),
                CombatStats.create(
                        maxHp = 25,
                        attackValue = 8,
                        defenseValue = 4),
                Inventory(2).apply {
                    addItem(newRandomWeapon())
                    addItem(newRandomArmor())
                },
                EntityActions(Attack::class))
        facets(Movable, Attackable, ItemDropper, LootDropper, Destructible)
        behaviors(HunterSeeker or Wanderer)
    }
}
