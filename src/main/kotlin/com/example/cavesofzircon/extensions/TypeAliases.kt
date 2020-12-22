package com.example.cavesofzircon.extensions

// put this in a file called TypeAliases.kt

import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType

typealias AnyGameEntity = GameEntity<EntityType>

typealias GameEntity<T> = Entity<T, GameContext>
