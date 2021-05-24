package com.example.cavesofzircon.world

import com.example.cavesofzircon.attributes.Vision
import com.example.cavesofzircon.blocks.GameBlock
import com.example.cavesofzircon.extensions.AnyGameEntity
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.blocksVision
import com.example.cavesofzircon.extensions.position
import org.hexworks.amethyst.api.Engine
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.internal.TurnBasedEngine
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.builder.game.GameAreaBuilder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.shape.EllipseFactory
import org.hexworks.zircon.api.shape.LineFactory
import org.hexworks.zircon.api.uievent.UIEvent

class World(
    startingBlocks: Map<Position3D, GameBlock>,
    visibleSize: Size3D,
    actualSize: Size3D
) : GameArea<Tile, GameBlock> by GameAreaBuilder.newBuilder<Tile, GameBlock>()
    .withVisibleSize(visibleSize)
    .withActualSize(actualSize)
    .build() {

    private val engine: TurnBasedEngine<GameContext> = Engine.create()

    init {
        startingBlocks.forEach { (pos, block) ->
            setBlockAt(pos, block)
            block.entities.forEach { entity ->
                engine.addEntity(entity)
                entity.position = pos
            }
        }
    }

    fun update(screen: Screen, uiEvent: UIEvent, game: Game) { // 1
        engine.executeTurn(
            GameContext( // 2
                world = this,
                screen = screen, // 3
                uiEvent = uiEvent, // 4
                player = game.player
            )
        ) // 5
    }

    fun moveEntity(entity: GameEntity<EntityType>, position: Position3D): Boolean { // 1
        var success = false                                                         // 2
        val oldBlock = fetchBlockAt(entity.position)
        val newBlock = fetchBlockAt(position)                       // 3

        if (bothBlocksPresent(oldBlock, newBlock)) {                                // 4
            success = true                                                          // 5
            oldBlock.get().removeEntity(entity)
            entity.position = position
            newBlock.get().addEntity(entity)
        }
        return success                                                              // 6
    }

    private fun bothBlocksPresent(oldBlock: Maybe<GameBlock>, newBlock: Maybe<GameBlock>) =  // 7
        oldBlock.isPresent && newBlock.isPresent

    /**
     * Adds the given [Entity] at the given [Position3D].
     * Has no effect if this world already contains the
     * given [Entity].
     */
    fun addEntity(
        entity: Entity<EntityType, GameContext>,
        position: Position3D
    ) {
        entity.position = position
        engine.addEntity(entity)
        fetchBlockAt(position).map {
            it.addEntity(entity)
        }
    }

    fun addAtEmptyPosition(
        entity: AnyGameEntity,
        offset: Position3D = Position3D.create(0, 0, 0),
        size: Size3D = actualSize
    ): Boolean {
        return findEmptyLocationWithin(offset, size).fold(
            whenEmpty = {
                false
            },
            whenPresent = { location ->
                addEntity(entity, location)
                true
            })

    }

    fun removeEntity(entity: Entity<EntityType, GameContext>) {
        fetchBlockAt(entity.position).map {
            it.removeEntity(entity)
        }
        engine.removeEntity(entity)
        entity.position = Position3D.unknown()
    }

    /**
     * Finds an empty location within the given area (offset and size) on this [World].
     */
    fun findEmptyLocationWithin(offset: Position3D, size: Size3D): Maybe<Position3D> {
        var position = Maybe.empty<Position3D>()
        val maxTries = 10
        var currentTry = 0
        while (position.isPresent.not() && currentTry < maxTries) {
            val pos = Position3D.create(
                x = (Math.random() * size.xLength).toInt() + offset.x,
                y = (Math.random() * size.yLength).toInt() + offset.y,
                z = (Math.random() * size.zLength).toInt() + offset.z
            )
            fetchBlockAt(pos).map {
                if (it.isEmptyFloor) {
                    position = Maybe.of(pos)
                }
            }
            currentTry++
        }
        return position
    }

    fun isVisionBlockedAt(pos: Position3D): Boolean {
        return fetchBlockAt(pos).fold(whenEmpty = { false }, whenPresent = {    // 1
            it.entities.any(GameEntity<EntityType>::blocksVision)               // 2
        })
    }

    fun findVisiblePositionsFor(entity: GameEntity<EntityType>): Iterable<Position> {
        val centerPos = entity.position.to2DPosition()                  // 3
        return entity.findAttribute(Vision::class).map { (radius) ->    // 4
            EllipseFactory.buildEllipse(                                // 5
                fromPosition = centerPos,
                toPosition = centerPos.withRelativeX(radius).withRelativeY(radius)
            )
                .positions
                .flatMap { ringPos ->
                    val result = mutableListOf<Position>()
                    val iter = LineFactory.buildLine(centerPos, ringPos).iterator() // 6
                    do {
                        val next = iter.next()
                        result.add(next)
                    } while (iter.hasNext() &&
                        isVisionBlockedAt(Position3D.from2DPosition(next, entity.position.z)).not()
                    )                                                               // 7
                    result
                }
        }.orElse(listOf())                                                          // 8
    }

    fun addWorldEntity(entity: Entity<EntityType, GameContext>) {
        engine.addEntity(entity)
    }
}
