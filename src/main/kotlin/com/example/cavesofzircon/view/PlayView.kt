package com.example.cavesofzircon.view

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment.LEFT_CENTER
import org.hexworks.zircon.api.component.ComponentAlignment.RIGHT_CENTER
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView

class PlayView(
    private val grid: TileGrid
) : BaseView(grid, ColorThemes.arc()) {
    init {
        val loseButton = Components.button()
            // constants like LEFT_CENTER can also be imported for brevity
            .withAlignmentWithin(screen, LEFT_CENTER)
            .withText("Lose!")
            .withDecorations(box(), shadow())
            .build()

        val winButton = Components.button()
            .withAlignmentWithin(screen, RIGHT_CENTER)
            .withText("Win!")
            .withDecorations(box(), shadow())
            .build()

        screen.addComponents(loseButton, winButton)

        loseButton.onActivated {
            replaceWith(LoseView(grid))
        }

        winButton.onActivated {
            replaceWith(WinView(grid))
        }
    }
}
