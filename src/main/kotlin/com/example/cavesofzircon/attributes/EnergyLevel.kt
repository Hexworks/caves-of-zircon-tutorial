package com.example.cavesofzircon.attributes

import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom

class EnergyLevel(
    initialEnergy: Int,
    val maxEnergy: Int
) : BaseAttribute() {

    var currentEnergy: Int
        get() = currentValueProperty.value
        set(value) {
            currentValueProperty.value = Math.min(value, maxEnergy)
        }

    private val currentValueProperty = createPropertyFrom(initialEnergy)

}
