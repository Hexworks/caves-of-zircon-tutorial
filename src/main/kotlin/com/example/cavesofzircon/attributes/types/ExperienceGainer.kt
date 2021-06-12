package com.example.cavesofzircon.attributes.types

import com.example.cavesofzircon.attributes.CombatStats
import com.example.cavesofzircon.attributes.Experience
import com.example.cavesofzircon.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType

interface ExperienceGainer : EntityType

val GameEntity<ExperienceGainer>.experience: Experience
    get() = findAttribute(Experience::class).get()

val GameEntity<ExperienceGainer>.combatStats: CombatStats
    get() = findAttribute(CombatStats::class).get()