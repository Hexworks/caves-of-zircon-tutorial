package com.example.cavesofzircon.view

import com.example.cavesofzircon.GameConfig
import com.example.cavesofzircon.world.GameBuilder
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView
import kotlin.system.exitProcess

class WinView(
        private val grid: TileGrid,
        private val zircons: Int
) : BaseView(grid, ColorThemes.arc()) {

    init {
        val msg = "You won!"
        val header = Components.textBox(GameConfig.WINDOW_WIDTH / 2)
                .addHeader(msg)
                .addNewLine()
                .addParagraph("Congratulations! You have escaped from Caves of Zircon!", withNewLine = false)
                .addParagraph("You've managed to find $zircons Zircons.")       // 1
                .withAlignmentWithin(screen, ComponentAlignment.CENTER)
                .build()
        val restartButton = Components.button()
                .withAlignmentAround(header, ComponentAlignment.BOTTOM_LEFT)    // 2
                .withText("Restart")
                .withDecorations(box(BoxType.SINGLE))
                .build()
        val exitButton = Components.button()
                .withAlignmentAround(header, ComponentAlignment.BOTTOM_RIGHT)   // 3
                .withText("Quit")
                .withDecorations(box(BoxType.SINGLE))
                .build()

        restartButton.onActivated {
            replaceWith(PlayView(grid, GameBuilder(
                    worldSize = GameConfig.WORLD_SIZE
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
