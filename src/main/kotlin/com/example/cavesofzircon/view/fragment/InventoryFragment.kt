package com.example.cavesofzircon.view.fragment

import com.example.cavesofzircon.attributes.Inventory
import com.example.cavesofzircon.extensions.GameItem
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.uievent.Processed

class InventoryFragment(
    inventory: Inventory,
    width: Int,
    onDrop: (GameItem) -> Unit
) : Fragment {

    override val root = Components.vbox()           // 1
        .withSize(width, inventory.size + 1)
        .build().apply {
            addComponent(Components.hbox()          // 2
                .withSpacing(1)
                .withSize(width, 1)
                .build().apply {
                    addComponent(Components.label().withText("").withSize(1, 1))
                    addComponent(Components.header().withText("Name").withSize(NAME_COLUMN_WIDTH, 1))
                    addComponent(Components.header().withText("Actions").withSize(ACTIONS_COLUMN_WIDTH, 1))
                }
            )
            inventory.items.forEach { item ->       // 3
                val row = InventoryRowFragment(width, item)
                addFragment(row).apply {
                    row.dropButton.onActivated {    // 4
                        detach()                    // 5
                        onDrop(item)                // 6
                        Processed
                    }
                }
            }
        }

    companion object {
        const val NAME_COLUMN_WIDTH = 15
        const val ACTIONS_COLUMN_WIDTH = 10
    }
}
