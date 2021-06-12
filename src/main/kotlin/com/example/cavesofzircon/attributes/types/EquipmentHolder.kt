package com.example.cavesofzircon.attributes.types

import com.example.cavesofzircon.attributes.Equipment
import com.example.cavesofzircon.attributes.Inventory
import com.example.cavesofzircon.extensions.GameCombatItem
import com.example.cavesofzircon.extensions.GameEquipmentHolder
import org.hexworks.amethyst.api.entity.EntityType

interface EquipmentHolder : EntityType

fun GameEquipmentHolder.equip(inventory: Inventory, item: GameCombatItem): GameCombatItem {
    return equipment.equip(inventory, item)
}

val GameEquipmentHolder.equipment: Equipment
    get() = findAttribute(Equipment::class).get()