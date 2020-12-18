package com.example.cavesofzircon

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.screen.Screen

fun main(args: Array<String>) {

    val grid = SwingApplications.startTileGrid()
    val screen = Screen.create(grid)

    screen.addComponent(
        Components.header()
            .withText("Hello, from Caves of Zircon!")
            .withAlignmentWithin(screen, ComponentAlignment.CENTER)
    )

    screen.theme = ColorThemes.arc()
    screen.display()

}
