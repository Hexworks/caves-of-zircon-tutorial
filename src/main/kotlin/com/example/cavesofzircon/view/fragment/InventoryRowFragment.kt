package com.example.cavesofzircon.view.fragment

import com.example.cavesofzircon.attributes.types.iconTile
import com.example.cavesofzircon.extensions.GameItem
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.graphics.Symbols

class InventoryRowFragment(width: Int, item: GameItem) : Fragment { // 1

    val dropButton = Components.button()                // 2
        .withText("${Symbols.ARROW_DOWN}")              // 3
        .withDecorations()                              // 4
        .build()

    override val root = Components.hbox()               // 5
        .withSpacing(1)                                 // 6
        .withSize(width, 1)
        .build().apply {
            addComponent(
                Components.icon()                       // 7
                    .withIcon(item.iconTile)
            )
            addComponent(
                Components.label()
                    .withSize(InventoryFragment.NAME_COLUMN_WIDTH, 1)   // 8
                    .withText(item.name)
            )
            addComponent(dropButton)
        }
}
