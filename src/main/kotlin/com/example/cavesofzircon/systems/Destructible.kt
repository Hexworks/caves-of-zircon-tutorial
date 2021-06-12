package com.example.cavesofzircon.systems

import com.example.cavesofzircon.messages.Destroy
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import com.example.cavesofzircon.functions.logGameEvent
import com.example.cavesofzircon.messages.EntityDestroyed

object Destructible : BaseFacet<GameContext, Destroy>(Destroy::class) {
    override suspend fun receive(message: Destroy): Response {
        val (context, destroyer, target, cause) = message
        context.world.removeEntity(target)
        destroyer.receiveMessage(EntityDestroyed(context, target, destroyer))
        logGameEvent("$target dies $cause.", Destructible)
        return Consumed
    }
}
