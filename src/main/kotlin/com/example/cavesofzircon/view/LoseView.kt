package com.example.cavesofzircon.view

import com.example.cavesofzircon.GameConfig.WORLD_SIZE
import com.example.cavesofzircon.world.GameBuilder
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView
import kotlin.system.exitProcess

class LoseView(
        private val grid: TileGrid,
        private val causeOfDeath: String
) : BaseView(grid, ColorThemes.arc()) {

    init {
        val msg = "Game Over"
        val header = Components.textBox(30)
                .addHeader(msg)
                .addParagraph(causeOfDeath)                 // 1
                .addNewLine()
                .withAlignmentWithin(screen, ComponentAlignment.CENTER)
                .build()
        val restartButton = Components.button()             // 2
                .withAlignmentAround(header, ComponentAlignment.BOTTOM_LEFT)
                .withText("Restart")
                .withDecorations(box(BoxType.SINGLE))
                .build()
        val exitButton = Components.button()                // 3
                .withAlignmentAround(header, ComponentAlignment.BOTTOM_RIGHT)
                .withText("Quit")
                .withDecorations(box(BoxType.SINGLE))
                .build()

        restartButton.onActivated {
            replaceWith(PlayView(grid, GameBuilder(
                    worldSize = WORLD_SIZE
            ).buildGame()))
        }

        exitButton.onActivated {
            exitProcess(0)
        }

        screen.addComponent(header)
        screen.addComponent(restartButton)
        screen.addComponent(exitButton)
    }
}
