package com.example.cavesofzircon.systems

import com.example.cavesofzircon.messages.Destroy
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import com.example.cavesofzircon.functions.logGameEvent

object Destructible : BaseFacet<GameContext, Destroy>(Destroy::class) {
    override suspend fun receive(message: Destroy): Response {
        val (context, _, target, cause) = message
        context.world.removeEntity(target)
        logGameEvent("$target dies after receiving $cause.", Destructible)
        return Consumed
    }
}
