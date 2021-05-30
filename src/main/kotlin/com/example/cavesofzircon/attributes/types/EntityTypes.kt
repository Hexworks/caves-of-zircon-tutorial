package com.example.cavesofzircon.attributes.types

import org.hexworks.amethyst.api.base.BaseEntityType

object Wall : BaseEntityType(
    name = "wall"
)

object Player : BaseEntityType(
    name = "player"
), Combatant, ItemHolder, EnergyUser

object Fungus : BaseEntityType(
    name = "fungus"
), Combatant

object StairsDown : BaseEntityType(
    name = "stairs down"
)

object StairsUp : BaseEntityType(
    name = "stairs up"
)

object FOW : BaseEntityType(
    name = "Fog of War"
)

object Bat : BaseEntityType(
    name = "bat"
), Combatant, ItemHolder

object Zircon : BaseEntityType(
    name = "Zircon",
    description = "A small piece of Zircon. Its value is unfathomable."
), Item

object BatMeat : BaseEntityType(
    name = "Bat meat",
    description = "Stringy bat meat. It is edible, but not tasty."
), Food
