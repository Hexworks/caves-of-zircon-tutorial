package com.example.cavesofzircon.messages

import com.example.cavesofzircon.extensions.GameItem
import com.example.cavesofzircon.extensions.GameItemHolder
import com.example.cavesofzircon.extensions.GameMessage
import com.example.cavesofzircon.world.GameContext
import org.hexworks.zircon.api.data.Position3D

data class DropItem(
    override val context: GameContext,
    override val source: GameItemHolder,
    val item: GameItem,
    val position: Position3D
) : GameMessage
