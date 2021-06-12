package com.example.cavesofzircon.view.fragment

import com.example.cavesofzircon.GameConfig
import com.example.cavesofzircon.attributes.Inventory
import com.example.cavesofzircon.extensions.GameItem
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.uievent.Processed

class InventoryFragment(
        inventory: Inventory,
        width: Int,
        private val onDrop: (GameItem) -> Unit,
        private val onEat: (GameItem) -> Unit,
        private val onEquip: (GameItem) -> Maybe<GameItem>,
        private val onExamine: (GameItem) -> Unit
) : Fragment {

    override val root = Components.vbox()
            .withSize(width, inventory.size + 1)
            .build().apply {
                val list = this

                addComponent(Components.hbox()
                        .withSpacing(1)
                        .withSize(width, 1)
                        .build().apply {
                            addComponent(Components.label().withText("").withSize(1, 1))
                            addComponent(Components.header().withText("Name").withSize(NAME_COLUMN_WIDTH, 1))
                            addComponent(Components.header().withText("Actions").withSize(ACTIONS_COLUMN_WIDTH, 1))
                        }
                )
                inventory.items.forEach { item ->       // 2
                    addRow(width, item, list)
                }
            }

    private fun addRow(width: Int, item: GameItem, list: VBox) {
        val row = InventoryRowFragment(width, item)
        list.addFragment(row).apply {
            row.dropButton.onActivated {
                detach()
                onDrop(item)
            }
            row.eatButton.onActivated {
                detach()
                onEat(item)
            }
            row.equipButton.onActivated {
                onEquip(item).map { oldItem ->          // 3
                    detach()
                    addRow(width, oldItem, list)
                }
            }
            row.examineButton.onActivated {
                onExamine(item)
            }
        }
        list.theme = GameConfig.THEME
    }

    companion object {
        const val NAME_COLUMN_WIDTH = 15
        const val ACTIONS_COLUMN_WIDTH = 10
    }
}
