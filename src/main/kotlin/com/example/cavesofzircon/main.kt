package com.example.cavesofzircon

import com.example.cavesofzircon.view.StartView
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig

fun main(args: Array<String>) {

    val grid = SwingApplications.startTileGrid(
        // a grid can be configured using the AppConfig builder
        AppConfig.newBuilder()
            // We can choose a tileset that will be used by default
            .withDefaultTileset(CP437TilesetResources.rogueYun16x16())
            .build()
    )
    StartView(grid).dock()
}
