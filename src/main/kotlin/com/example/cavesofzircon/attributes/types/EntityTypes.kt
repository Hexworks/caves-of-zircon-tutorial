package com.example.cavesofzircon.attributes.types

import org.hexworks.amethyst.api.base.BaseEntityType

object Wall : BaseEntityType(
    name = "wall"
)

object Player : BaseEntityType(
    name = "player"
), Combatant

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
