package com.example.cavesofzircon.systems

import com.example.cavesofzircon.messages.Dig
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object Diggable : BaseFacet<GameContext, Dig>(Dig::class) {
    override suspend fun receive(message: Dig): Response {
        val (context, _, target) = message
        context.world.removeEntity(target)          // 1
        return Consumed                             // 2
    }
}