package com.example.cavesofzircon.extensions

import com.example.cavesofzircon.attributes.EntityPosition
import com.example.cavesofzircon.attributes.EntityTile
import org.hexworks.amethyst.api.Attribute
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

                                                                    // 4
fun <T : Attribute> AnyGameEntity.tryToFindAttribute(klass: KClass<T>): T = findAttribute(klass).orElseThrow {
    NoSuchElementException("Entity '$this' has no property with type '${klass.simpleName}'.")
}
