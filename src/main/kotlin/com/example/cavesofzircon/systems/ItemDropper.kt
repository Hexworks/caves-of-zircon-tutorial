package com.example.cavesofzircon.systems

import com.example.cavesofzircon.attributes.types.removeItem
import com.example.cavesofzircon.extensions.isPlayer
import com.example.cavesofzircon.functions.logGameEvent
import com.example.cavesofzircon.messages.DropItem
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object ItemDropper : BaseFacet<GameContext, DropItem>(DropItem::class) {
    override suspend fun receive(message: DropItem): Response {
        val (context, itemHolder, item, position) = message // 1
        if (itemHolder.removeItem(item)) {                  // 2
            context.world.addEntity(item, position)         // 3
            val subject = if (itemHolder.isPlayer) "You" else "The $itemHolder"
            val verb = if (itemHolder.isPlayer) "drop" else "drops"
            logGameEvent("$subject $verb the $item.", ItemDropper)
        }
        return Consumed
    }
}

