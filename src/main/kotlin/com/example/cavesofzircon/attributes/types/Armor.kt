package com.example.cavesofzircon.attributes.types

import com.example.cavesofzircon.attributes.ItemCombatStats
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.entity.Entity

interface Armor : CombatItem

val Entity<Armor, GameContext>.attackValue: Int
    get() = findAttribute(ItemCombatStats::class).get().attackValue

val Entity<Armor, GameContext>.defenseValue: Int
    get() = findAttribute(ItemCombatStats::class).get().defenseValue