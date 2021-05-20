package com.example.cavesofzircon.builders

import com.example.cavesofzircon.blocks.GameBlock
import com.example.cavesofzircon.extensions.sameLevelNeighborsShuffled
import com.example.cavesofzircon.world.World
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import kotlin.random.Random

class WorldBuilder(private val worldSize: Size3D) {

    private var blocks: MutableMap<Position3D, GameBlock> = mutableMapOf()
    private val depth = worldSize.yLength
    private val width = worldSize.xLength
    private val height = worldSize.zLength

    fun makeCaves(): WorldBuilder {
        return randomizeTiles()
            .smooth(8)
            .connectLevels()
    }

    fun build(visibleSize: Size3D): World = World(blocks, visibleSize, worldSize)

    private fun randomizeTiles(): WorldBuilder {
        forAllPositions { pos ->
            blocks[pos] = if (Math.random() < 0.5) {
                GameBlockFactory.floor()
            } else GameBlockFactory.wall()
        }
        return this
    }

    private fun smooth(iterations: Int): WorldBuilder {
        val newBlocks = mutableMapOf<Position3D, GameBlock>()
        repeat(iterations) {
            forAllPositions { pos ->
                val (x, y, z) = pos
                var floors = 0
                var rocks = 0
                pos.sameLevelNeighborsShuffled().plus(pos).forEach { neighbor ->
                    blocks.whenPresent(neighbor) { block ->
                        if (block.isEmptyFloor) {
                            floors++
                        } else rocks++
                    }
                }
                newBlocks[Position3D.create(x, y, z)] =
                    if (floors >= rocks) GameBlockFactory.floor() else GameBlockFactory.wall()
            }
            blocks = newBlocks
        }
        return this
    }

    private fun forAllPositions(fn: (Position3D) -> Unit) {
        worldSize.fetchPositions().forEach(fn)
    }

    private fun MutableMap<Position3D, GameBlock>.whenPresent(pos: Position3D, fn: (GameBlock) -> Unit) {
        this[pos]?.let(fn)
    }

    private fun connectLevels() = also {
        (height - 1).downTo(1).forEach(::connectRegionDown) // 2
    }

    private fun generateRandomFloorPositionsOn(level: Int) = sequence { // 1
        while (true) {                                                  // 2
            var pos = Position3D.unknown()                              // 3
            while (pos.isUnknown) {                                     // 4
                val candidate = Position3D.create(                      // 5
                    x = Random.nextInt(width - 1),
                    y = Random.nextInt(depth - 1),
                    z = level
                )
                if (blocks[candidate].isEmptyFloor()) {                 // 6
                    pos = candidate
                }
            }
            yield(pos)                                                  // 7
        }
    }

    private fun GameBlock?.isEmptyFloor(): Boolean {                    // 8
        return this?.isEmptyFloor ?: false
    }

    private fun connectRegionDown(currentLevel: Int) {                      // 1
        val posToConnect = generateRandomFloorPositionsOn(currentLevel)     // 2
            .first { pos ->                                                 // 3
                blocks[pos].isEmptyFloor() && blocks[pos.below()].isEmptyFloor()    // 4
            }
        blocks[posToConnect] = GameBlockFactory.stairsDown()                        // 5
        blocks[posToConnect.below()] = GameBlockFactory.stairsUp()

    }

    private fun Position3D.below() = copy(z = z - 1)
}
