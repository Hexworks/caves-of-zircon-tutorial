package com.example.cavesofzircon.view.dialog

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class HelpDialog(screen: Screen) : Dialog(screen) {

    override val container = Components.panel()
            .withDecorations(box(title = "Help", boxType = BoxType.TOP_BOTTOM_DOUBLE))
            .withSize(50, 30)
            .build().apply {

                addComponent(Components.vbox()
                        .withSize(contentSize.width, contentSize.height - 1)
                        .withSpacing(2)
                        .build().apply {
                            addComponent(Components.textBox(contentSize.width)
                                    .addNewLine()
                                    .addHeader("Caves of Zircon")
                                    .addParagraph("""
                            Descend to the Caves Of Zircon and collect as many Zircons as you can.
                             Find the exit (+) to win the game. Use what you find to avoid dying.""".trimIndent()
                                    )
                            )

                            addComponent(Components.textBox(40)
                                    .addHeader("Navigation:")
                                    .addListItem("[Tab]: Focus next")
                                    .addListItem("[Shift] + [Tab]: Focus previous")
                                    .addListItem("[Space]: Activate focused item")
                            )

                            addComponent(Components.hbox()
                                    .withSize(width, 10)
                                    .build().apply {
                                        addComponent(Components.textBox(width / 2)
                                                .addHeader("Movement:")
                                                .addListItem("wasd: Movement")
                                                .addListItem("r: Move up")
                                                .addListItem("f: Move down")
                                        )

                                        addComponent(Components.textBox(width / 2)
                                                .addHeader("Actions:")
                                                .addListItem("(i)nventory")
                                                .addListItem("(p)ick up")
                                                .addListItem("(h)elp")
                                        )
                                    })
                        })
            }
}