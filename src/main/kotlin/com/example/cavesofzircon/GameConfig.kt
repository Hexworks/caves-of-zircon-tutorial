package com.example.cavesofzircon

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size3D

object GameConfig {

    // game
    const val DUNGEON_LEVELS = 2                        // 1

    // look & feel
    val TILESET = CP437TilesetResources.rogueYun16x16() // 2
    val THEME = ColorThemes.zenburnVanilla()            // 3
    const val SIDEBAR_WIDTH = 18
    const val LOG_AREA_HEIGHT = 8                       // 4

    // sizing
    const val WINDOW_WIDTH = 80
    const val WINDOW_HEIGHT = 50

    val WORLD_SIZE = Size3D.create(WINDOW_WIDTH, WINDOW_HEIGHT, DUNGEON_LEVELS)
    val GAME_COMPONENT_SIZE = Size3D.create(
        xLength = WINDOW_WIDTH - SIDEBAR_WIDTH,
        yLength = WINDOW_HEIGHT - LOG_AREA_HEIGHT,
        zLength = 1
    )

    fun buildAppConfig() = AppConfig.newBuilder()       // 5
        .withDefaultTileset(TILESET)
        .withSize(WINDOW_WIDTH, WINDOW_HEIGHT)
        .build()

}
