package com.example.cavesofzircon.systems

import com.example.cavesofzircon.attributes.types.StairsDown
import com.example.cavesofzircon.blocks.GameBlock
import com.example.cavesofzircon.extensions.position
import com.example.cavesofzircon.functions.logGameEvent
import com.example.cavesofzircon.messages.MoveDown
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object StairDescender : BaseFacet<GameContext, MoveDown>(MoveDown::class) {

    override suspend fun receive(message: MoveDown): Response {
        val (context, player) = message
        val world = context.world
        val playerPos = player.position
        world.fetchBlockAt(playerPos).map { block ->
            if (block.hasStairsDown) {
                logGameEvent("You move down one level...", StairDescender)
                world.moveEntity(player, playerPos.withRelativeZ(-1))
                world.scrollOneDown()
            } else {
                logGameEvent("You search for a trapdoor, but you find nothing.", StairDescender)
            }
        }
        return Consumed
    }

    private val GameBlock.hasStairsDown: Boolean
        get() = this.entities.any { it.type == StairsDown }
}
