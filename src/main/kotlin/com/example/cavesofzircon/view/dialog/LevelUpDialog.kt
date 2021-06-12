package com.example.cavesofzircon.view.dialog

import com.example.cavesofzircon.attributes.CombatStats
import com.example.cavesofzircon.attributes.Vision
import com.example.cavesofzircon.attributes.types.Player
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.tryToFindAttribute
import com.example.cavesofzircon.functions.logGameEvent
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

class LevelUpDialog(
        screen: Screen,
        player: GameEntity<Player>
) : Dialog(screen, false) {

    override val container = Components.vbox()
            .withDecorations(box(title = "Ding!", boxType = BoxType.TOP_BOTTOM_DOUBLE))
            .withSize(30, 15)
            .build().apply {
                val stats = player.tryToFindAttribute(CombatStats::class)
                val vision = player.tryToFindAttribute(Vision::class)

                addComponent(Components.textBox(27)
                        .addHeader("Congratulations, you leveled up!")
                        .addParagraph("Pick an improvement from the options below:"))   // 2

                addComponent(Components.button()                                        // 3
                        .withText("Max HP")
                        .build().apply {
                            onActivated {
                                stats.maxHpProperty.value += 10
                                logGameEvent("You look healthier.", this)
                                root.close(EmptyModalResult)
                            }
                        })


                addComponent(Components.button()
                        .withText("Attack")
                        .build().apply {
                            onActivated {
                                stats.attackValueProperty.value += 2
                                logGameEvent("You look stronger.", this)
                                root.close(EmptyModalResult)
                            }
                        })

                addComponent(Components.button()
                        .withText("Defense")
                        .build().apply {
                            onActivated {
                                stats.defenseValueProperty.value += 2
                                logGameEvent("You look tougher.", this)
                                root.close(EmptyModalResult)
                            }
                        })

                addComponent(Components.button()
                        .withText("Vision")
                        .build().apply {
                            onActivated {
                                vision.radius++                                 // 4
                                logGameEvent("You look more perceptive.", this)
                                root.close(EmptyModalResult)
                            }
                        })
            }
}