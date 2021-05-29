package com.example.cavesofzircon.systems

import com.example.cavesofzircon.attributes.types.Item
import com.example.cavesofzircon.attributes.types.addItem
import com.example.cavesofzircon.extensions.GameItem
import com.example.cavesofzircon.extensions.filterType
import com.example.cavesofzircon.extensions.isPlayer
import com.example.cavesofzircon.functions.logGameEvent
import com.example.cavesofzircon.messages.PickItemUp
import com.example.cavesofzircon.world.GameContext
import com.example.cavesofzircon.world.World
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.data.Position3D

object ItemPicker : BaseFacet<GameContext, PickItemUp>(PickItemUp::class) {

    override suspend fun receive(message: PickItemUp): Response {
        val (context, itemHolder, position) = message
        val world = context.world
        world.findTopItem(position).map { item ->                                   // 1
            if (itemHolder.addItem(item)) {                                         // 2
                world.removeEntity(item)                                            // 3
                val subject = if (itemHolder.isPlayer) "You" else "The $itemHolder" // 4
                val verb = if (itemHolder.isPlayer) "pick up" else "picks up"
                logGameEvent("$subject $verb the $item.", ItemPicker)
            }
        }
        return Consumed
    }

    private fun World.findTopItem(position: Position3D): Maybe<GameItem> =
        fetchBlockAt(position).flatMap { block ->                                   // 5
            Maybe.ofNullable(block.entities.filterType<Item>().firstOrNull())
        }

}
