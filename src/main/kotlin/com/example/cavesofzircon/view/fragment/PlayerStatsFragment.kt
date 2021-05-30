package com.example.cavesofzircon.view.fragment

import com.example.cavesofzircon.attributes.DisplayableAttribute
import com.example.cavesofzircon.attributes.types.Player
import com.example.cavesofzircon.extensions.GameEntity
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment

class PlayerStatsFragment(
    width: Int,
    player: GameEntity<Player>
) : Fragment {

    override val root = Components.vbox()
        .withSize(width, 30)                                                // 1
        .withSpacing(1)                                                    // 2
        .build().apply {
            addComponent(Components.header().withText("Player"))                // 3
            player.attributes.toList().filterIsInstance<DisplayableAttribute>()     // 4
                .forEach {
                    addComponent(it.toComponent(width))                             // 5
                }
        }
}
