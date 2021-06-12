package com.example.cavesofzircon.systems

import com.example.cavesofzircon.attributes.ZirconCounter
import com.example.cavesofzircon.attributes.types.Zircon
import com.example.cavesofzircon.attributes.types.ZirconHolder
import com.example.cavesofzircon.attributes.types.zirconCounter
import com.example.cavesofzircon.extensions.whenTypeIs
import com.example.cavesofzircon.functions.logGameEvent
import com.example.cavesofzircon.messages.PickItemUp
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object ZirconGatherer : BaseFacet<GameContext, PickItemUp>(PickItemUp::class, ZirconCounter::class) {

    override suspend fun receive(message: PickItemUp): Response {
        val (context, source, position) = message
        var response: Response = Pass
        val world = context.world
        world.findTopItem(position).map { item ->               // 1
            source.whenTypeIs<ZirconHolder> { zirconHolder ->   // 2
                if (item.type == Zircon) {                      // 3
                    zirconHolder.zirconCounter.zirconCount++    // 4
                    world.removeEntity(item)                    // 5
                    logGameEvent("$zirconHolder picked up a Zircon!", ZirconGatherer)
                    response = Consumed
                }
            }
        }
        return response
    }


}