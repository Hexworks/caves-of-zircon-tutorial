package com.example.cavesofzircon.attributes

import com.example.cavesofzircon.extensions.GameItem
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.datatypes.Maybe

class Inventory(val size: Int) : BaseAttribute() {                  // 1

    private val currentItems = mutableListOf<GameItem>()

    val items: List<GameItem>
        get() = currentItems.toList()

    val isEmpty: Boolean                                            // 2
        get() = currentItems.isEmpty()

    val isFull: Boolean                                             // 3
        get() = currentItems.size >= size

    fun findItemBy(id: UUID): Maybe<GameItem> {                     // 4
        return Maybe.ofNullable(items.firstOrNull { it.id == id })
    }

    fun addItem(item: GameItem): Boolean {                          // 5
        return if (isFull.not()) {
            currentItems.add(item)
        } else false
    }

    fun removeItem(entity: GameItem): Boolean {                     // 6
        return currentItems.remove(entity)
    }
}
