package com.example.cavesofzircon.attributes.types

import com.example.cavesofzircon.attributes.CombatStats
import com.example.cavesofzircon.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType

interface Combatant : EntityType                    // 1

val GameEntity<Combatant>.combatStats: CombatStats  // 2
    get() = findAttribute(CombatStats::class).get()
