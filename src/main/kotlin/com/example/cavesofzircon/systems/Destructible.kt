package com.example.cavesofzircon.systems

import com.example.cavesofzircon.events.PlayerDied
import com.example.cavesofzircon.extensions.isPlayer
import com.example.cavesofzircon.functions.logGameEvent
import com.example.cavesofzircon.messages.Destroy
import com.example.cavesofzircon.messages.EntityDestroyed
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.zircon.internal.Zircon

object Destructible : BaseFacet<GameContext, Destroy>(Destroy::class) {
    override suspend fun receive(message: Destroy): Response {
        val (context, destroyer, target, cause) = message
        context.world.removeEntity(target)
        destroyer.receiveMessage(EntityDestroyed(context, target, destroyer))
        destroyer.receiveMessage(EntityDestroyed(context, target, destroyer))
        if (target.isPlayer) {
            Zircon.eventBus.publish(PlayerDied("You died $cause", Destructible))
        }
        logGameEvent("$target dies $cause.", Destructible)
        return Consumed
    }
}
