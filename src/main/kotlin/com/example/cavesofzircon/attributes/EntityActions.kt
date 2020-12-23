package com.example.cavesofzircon.attributes

import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.messages.EntityAction
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.amethyst.api.entity.EntityType
import kotlin.reflect.KClass

class EntityActions(
        private vararg val actions: KClass<out EntityAction<out EntityType, out EntityType>> // 1
) : BaseAttribute() {


    fun createActionsFor(                                               // 2
            context: GameContext,
            source: GameEntity<EntityType>,
            target: GameEntity<EntityType>
    ): Iterable<EntityAction<out EntityType, out EntityType>> {
        return actions.map {
            try {
                it.constructors.first().call(context, source, target)   // 3
            } catch (e: Exception) {                                    // 4
                throw IllegalArgumentException("Can't create EntityAction. Does it have the proper constructor?")
            }
        }
    }
}