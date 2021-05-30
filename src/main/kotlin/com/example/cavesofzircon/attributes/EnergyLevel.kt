package com.example.cavesofzircon.attributes

import com.example.cavesofzircon.extensions.toStringProperty
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.cobalt.databinding.api.binding.bindPlusWith
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.Components

class EnergyLevel(
    initialEnergy: Int,
    val maxEnergy: Int
) : BaseAttribute(), DisplayableAttribute {

    var currentEnergy: Int
        get() = currentValueProperty.value
        set(value) {
            currentValueProperty.value = Math.min(value, maxEnergy)
        }

    private val currentValueProperty = createPropertyFrom(initialEnergy)

    override fun toComponent(width: Int) = Components.vbox()
        .withSize(width, 5)                         // 1
        .build().apply {

            val hungerLabel = Components.label()    // 2
                .withSize(width, 1)
                .build()

            hungerLabel.textProperty.updateFrom(
                currentValueProperty.toStringProperty() // 3
                    .bindPlusWith("/".toProperty())
                    .bindPlusWith(maxEnergy.toString().toProperty())
            )

            addComponent(
                Components.textBox(width)   // 4
                    .addHeader("Hunger")
            )
            addComponent(hungerLabel)

        }

}
