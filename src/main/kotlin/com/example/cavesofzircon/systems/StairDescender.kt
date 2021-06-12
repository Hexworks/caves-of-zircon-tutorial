package com.example.cavesofzircon.systems

import com.example.cavesofzircon.attributes.types.Exit
import com.example.cavesofzircon.attributes.types.Player
import com.example.cavesofzircon.attributes.types.StairsDown
import com.example.cavesofzircon.attributes.types.zirconCounter
import com.example.cavesofzircon.blocks.GameBlock
import com.example.cavesofzircon.events.PlayerWonTheGame
import com.example.cavesofzircon.extensions.position
import com.example.cavesofzircon.extensions.whenTypeIs
import com.example.cavesofzircon.functions.logGameEvent
import com.example.cavesofzircon.messages.MoveDown
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.zircon.internal.Zircon

object StairDescender : BaseFacet<GameContext, MoveDown>(MoveDown::class) {

    override suspend fun receive(message: MoveDown): Response {
        val (context, source) = message
        val world = context.world
        val pos = source.position
        world.fetchBlockAt(pos).map { block ->
            when {
                block.hasStairsDown -> {
                    logGameEvent("You move down one level...", StairDescender)
                    world.moveEntity(source, pos.withRelativeZ(-1))
                    world.scrollOneDown()
                }
                block.hasExit -> source.whenTypeIs<Player> {    // 1
                    Zircon.eventBus.publish(PlayerWonTheGame(it.zirconCounter.zirconCount, StairDescender))
                }
                else -> logGameEvent("You search for a trapdoor, but you find nothing.", StairDescender)
            }
        }
        return Consumed
    }

    private val GameBlock.hasStairsDown: Boolean
        get() = this.entities.any { it.type == StairsDown }

    private val GameBlock.hasExit: Boolean              // 2
        get() = this.entities.any { it.type == Exit }
}
