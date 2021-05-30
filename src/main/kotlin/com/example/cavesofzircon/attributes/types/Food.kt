package com.example.cavesofzircon.attributes.types

import com.example.cavesofzircon.attributes.NutritionalValue
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.tryToFindAttribute

interface Food : Item

val GameEntity<Food>.energy: Int
    get() = tryToFindAttribute(NutritionalValue::class).energy
