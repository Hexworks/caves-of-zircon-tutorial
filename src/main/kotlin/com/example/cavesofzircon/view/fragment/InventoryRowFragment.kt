package com.example.cavesofzircon.view.fragment

import com.example.cavesofzircon.attributes.types.CombatItem
import com.example.cavesofzircon.attributes.types.Food
import com.example.cavesofzircon.attributes.types.iconTile
import com.example.cavesofzircon.extensions.GameItem
import com.example.cavesofzircon.extensions.whenTypeIs
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment

class InventoryRowFragment(width: Int, item: GameItem) : Fragment {

    val dropButton = Components.button()    // 1
        .withDecorations()
        .withText("Drop")
        .build()

    val eatButton = Components.button()     // 2
        .withDecorations()
        .withText("Eat")
        .build()

    val equipButton = Components.button()
            .withDecorations()
            .withText("Equip")
            .build()

    override val root = Components.hbox()
        .withSpacing(1)
        .withSize(width, 1)
        .build().apply {
            addComponent(
                Components.icon()
                    .withIcon(item.iconTile)
            )
            addComponent(
                Components.label()
                    .withSize(InventoryFragment.NAME_COLUMN_WIDTH, 1)
                    .withText(item.name)
            )
            addComponent(dropButton)
            item.whenTypeIs<Food> {     // 3
                addComponent(eatButton)
            }
            item.whenTypeIs<CombatItem> {
                addComponent(equipButton)
            }
        }
}
