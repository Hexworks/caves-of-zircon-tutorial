package com.example.cavesofzircon.world

import com.example.cavesofzircon.blocks.GameBlock
import org.hexworks.zircon.api.builder.game.GameAreaBuilder
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea

class World(
    startingBlocks: Map<Position3D, GameBlock>,         // 1
    visibleSize: Size3D,
    actualSize: Size3D                                  // 2
) : GameArea<Tile, GameBlock> by GameAreaBuilder.newBuilder<Tile, GameBlock>()
    .withVisibleSize(visibleSize)                       // 3
    .withActualSize(actualSize)                         // 4
    .build() {

    init {
        startingBlocks.forEach { (pos, block) ->
            setBlockAt(pos, block)                      // 5
        }
    }
}
