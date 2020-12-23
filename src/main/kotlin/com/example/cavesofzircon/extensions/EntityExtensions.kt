package com.example.cavesofzircon.extensions

import com.example.cavesofzircon.attributes.EntityActions
import com.example.cavesofzircon.attributes.EntityPosition
import com.example.cavesofzircon.attributes.EntityTile
import com.example.cavesofzircon.attributes.flags.BlockOccupier
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.zircon.api.data.Tile
import kotlin.reflect.KClass

var AnyGameEntity.position                                          // 1
    get() = tryToFindAttribute(EntityPosition::class).position      // 2
    set(value) {                                                    // 3
        findAttribute(EntityPosition::class).map {
            it.position = value
        }
    }

val AnyGameEntity.tile: Tile
    get() = this.tryToFindAttribute(EntityTile::class).tile

val AnyGameEntity.occupiesBlock: Boolean
    get() = findAttribute(BlockOccupier::class).isPresent

// 4
fun <T : Attribute> AnyGameEntity.tryToFindAttribute(klass: KClass<T>): T = findAttribute(klass).orElseThrow {
    NoSuchElementException("Entity '$this' has no property with type '${klass.simpleName}'.")
}

suspend fun AnyGameEntity.tryActionsOn(context: GameContext, target: AnyGameEntity): Response { // 1
    var result: Response = Pass                                         // 2
    findAttributeOrNull(EntityActions::class)?.let {                    // 3
        it.createActionsFor(context, this, target).forEach { action ->
            if (target.receiveMessage(action) is Consumed) {            // 4
                result = Consumed
                return@forEach                                          // 5
            }
        }
    }
    return result
}
