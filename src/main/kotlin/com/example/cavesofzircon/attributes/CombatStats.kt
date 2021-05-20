package com.example.cavesofzircon.attributes

import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.cobalt.databinding.api.property.Property

data class CombatStats(
    val maxHpProperty: Property<Int>,                                           // 1
    val hpProperty: Property<Int> = createPropertyFrom(maxHpProperty.value),    // 2
    val attackValueProperty: Property<Int>,
    val defenseValueProperty: Property<Int>
) : BaseAttribute() {                               // 3

    val maxHp: Int by maxHpProperty.asDelegate()    // 4
    var hp: Int by hpProperty.asDelegate()          // 5
    val attackValue: Int by attackValueProperty.asDelegate()
    val defenseValue: Int by defenseValueProperty.asDelegate()

    companion object {

        fun create(maxHp: Int, hp: Int = maxHp, attackValue: Int, defenseValue: Int) =  // 6
            CombatStats(
                maxHpProperty = createPropertyFrom(maxHp),
                hpProperty = createPropertyFrom(hp),
                attackValueProperty = createPropertyFrom(attackValue),
                defenseValueProperty = createPropertyFrom(defenseValue)
            )
    }
}
