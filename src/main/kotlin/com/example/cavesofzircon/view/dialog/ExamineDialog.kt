package com.example.cavesofzircon.view.dialog

import com.example.cavesofzircon.attributes.types.iconTile
import com.example.cavesofzircon.extensions.GameItem
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class ExamineDialog(screen: Screen, item: GameItem) : Dialog(screen) {

    override val container = Components.panel()
            .withDecorations(box(
                    title = "Examining ${item.name}",
                    boxType = BoxType.TOP_BOTTOM_DOUBLE
            ))
            .withSize(25, 15)
            .build().apply {
                addComponent(Components.textBox(23)
                        .addHeader("Name", withNewLine = false)
                        .addInlineComponent(Components.icon()
                                .withIcon(item.iconTile)
                                .withTileset(GraphicalTilesetResources.nethack16x16())
                                .build())
                        .addInlineComponent(Components.label()
                                .withText(" ${item.name}")
                                .build())
                        .commitInlineElements()
                        .addNewLine()
                        .addHeader("Description", withNewLine = false)
                        .addParagraph(item.description, withNewLine = false))
            }
}