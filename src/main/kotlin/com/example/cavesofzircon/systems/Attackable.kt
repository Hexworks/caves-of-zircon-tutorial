package com.example.cavesofzircon.systems

import com.example.cavesofzircon.attributes.types.combatStats
import com.example.cavesofzircon.extensions.hasNoHealthLeft
import com.example.cavesofzircon.extensions.isPlayer
import com.example.cavesofzircon.functions.logGameEvent
import com.example.cavesofzircon.messages.Attack
import com.example.cavesofzircon.messages.Destroy
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import kotlin.math.max

object Attackable : BaseFacet<GameContext, Attack>(Attack::class) {

    override suspend fun receive(message: Attack): Response {
        val (context, attacker, target) = message

        return if (attacker.isPlayer || target.isPlayer) { // 1

            val damage = max(0, attacker.combatStats.attackValue - target.combatStats.defenseValue)
            val finalDamage = (Math.random() * damage).toInt() + 1
            target.combatStats.hp -= finalDamage

            logGameEvent("The $attacker hits the $target for $finalDamage!", Attackable)

            if (target.hasNoHealthLeft()) {
                target.sendMessage(
                    Destroy(
                        context = context,
                        source = attacker,
                        target = target,
                        cause = "after receiving a blow to the head"
                    )
                )
            }
            Consumed
        } else Pass
    }
}
