package com.example.cavesofzircon.systems

import com.example.cavesofzircon.messages.Attack
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object Attackable : BaseFacet<GameContext, Attack>(Attack::class) {
    override suspend fun receive(message: Attack): Response {
        val (context, _, target) = message
        context.world.removeEntity(target)
        return Consumed
    }
}
