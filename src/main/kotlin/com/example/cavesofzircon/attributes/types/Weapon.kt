package com.example.cavesofzircon.attributes.types

import com.example.cavesofzircon.attributes.ItemCombatStats
import com.example.cavesofzircon.extensions.GameEntity

interface Weapon : CombatItem

val GameEntity<Weapon>.attackValue: Int
    get() = findAttribute(ItemCombatStats::class).get().attackValue

val GameEntity<Weapon>.defenseValue: Int
    get() = findAttribute(ItemCombatStats::class).get().defenseValue