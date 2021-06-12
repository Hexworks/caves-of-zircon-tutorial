    package com.example.cavesofzircon.systems

    import com.example.cavesofzircon.attributes.CombatStats
    import com.example.cavesofzircon.attributes.types.ExperienceGainer
    import com.example.cavesofzircon.attributes.types.combatStats
    import com.example.cavesofzircon.attributes.types.experience
    import com.example.cavesofzircon.events.PlayerGainedLevel
    import com.example.cavesofzircon.extensions.attackValue
    import com.example.cavesofzircon.extensions.defenseValue
    import com.example.cavesofzircon.extensions.isPlayer
    import com.example.cavesofzircon.extensions.whenTypeIs
    import com.example.cavesofzircon.functions.logGameEvent
    import com.example.cavesofzircon.messages.EntityDestroyed
    import com.example.cavesofzircon.world.GameContext
    import org.hexworks.amethyst.api.Consumed
    import org.hexworks.amethyst.api.Response
    import org.hexworks.amethyst.api.base.BaseFacet
    import org.hexworks.zircon.internal.Zircon
    import kotlin.math.min

    object ExperienceAccumulator : BaseFacet<GameContext, EntityDestroyed>(EntityDestroyed::class) {
        override suspend fun receive(message: EntityDestroyed): Response {
            val (_, defender, attacker) = message
            attacker.whenTypeIs<ExperienceGainer> { experienceGainer ->   // 1
                val xp = experienceGainer.experience
                val stats = experienceGainer.combatStats
                val defenderHp = defender.findAttribute(CombatStats::class).map { it.maxHp }.orElse(0)      // 2
                val amount = (defenderHp + defender.attackValue + defender.defenseValue) - xp.currentLevel * 2   // 3
                if (amount > 0) {
                    xp.currentXP += amount
                    while (xp.currentXP > Math.pow(xp.currentLevel.toDouble(), 1.5) * 20) {                      // 4
                        xp.currentLevel++
                        logGameEvent("$attacker advanced to level ${xp.currentLevel}.", ExperienceAccumulator)
                        stats.hpProperty.value = min(stats.hp + xp.currentLevel * 2, stats.maxHp)             // 5
                        if (attacker.isPlayer) {
                            Zircon.eventBus.publish(PlayerGainedLevel(ExperienceAccumulator))                   // 6
                        }
                    }
                }

            }
            return Consumed
        }
    }