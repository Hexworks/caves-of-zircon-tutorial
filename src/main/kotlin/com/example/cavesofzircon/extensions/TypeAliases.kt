package com.example.cavesofzircon.extensions

import com.example.cavesofzircon.attributes.types.Item
import com.example.cavesofzircon.attributes.types.ItemHolder
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType

typealias AnyGameEntity = GameEntity<EntityType>

typealias GameEntity<T> = Entity<T, GameContext>

typealias GameMessage = Message<GameContext>

typealias GameItem = GameEntity<Item>

typealias GameItemHolder = GameEntity<ItemHolder>
