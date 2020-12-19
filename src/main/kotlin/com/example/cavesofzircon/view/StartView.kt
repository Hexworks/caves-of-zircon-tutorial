package com.example.cavesofzircon.view

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView

class StartView constructor(
    private val grid: TileGrid
) : BaseView(grid, ColorThemes.arc()) {
    init {
        val msg = "Welcome to Caves of Zircon."

        // a text box can hold headers, paragraphs and list items
        // contentWidth =  here is a so called keyword parameter
        // using them you can pass parameters not by their order
        // but by their name.
        // this might be familiar for Python programmers
        val header = Components.textBox(contentWidth = msg.length)
            // we add a header
            .addHeader(msg)
            // and a new line
            .addNewLine()
            // and align it to center
            .withAlignmentWithin(screen, ComponentAlignment.CENTER)
            .build() // finally we build the component

        val startButton = Components.button()
            // we align the button to the bottom center of our header
            .withAlignmentAround(header, ComponentAlignment.BOTTOM_CENTER)
            // its text is "Start!"
            .withText("Start!")
            // we want a box and some shadow around it
            .withDecorations(box(), shadow())
            .build()

        startButton.onActivated {
            replaceWith(PlayView(grid)) // 1
        }

        // We can add multiple components at once
        screen.addComponents(header, startButton)
    }
}
