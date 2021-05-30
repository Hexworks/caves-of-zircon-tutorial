package com.example.cavesofzircon.systems

import com.example.cavesofzircon.attributes.types.ItemHolder
import com.example.cavesofzircon.attributes.types.inventory
import com.example.cavesofzircon.extensions.position
import com.example.cavesofzircon.extensions.whenTypeIs
import com.example.cavesofzircon.messages.Destroy
import com.example.cavesofzircon.messages.DropItem
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object LootDropper : BaseFacet<GameContext, Destroy>(Destroy::class) {

    override suspend fun receive(message: Destroy): Response {
        val (context, _, target) = message              // 1
        target.whenTypeIs<ItemHolder> { entity ->       // 2
            entity.inventory.items.forEach { item ->
                entity.receiveMessage(DropItem(context, entity, item, entity.position))     // 3
            }
        }
        return Pass                                     // 4
    }
}
