package com.example.cavesofzircon.attributes.types

import com.example.cavesofzircon.attributes.ZirconCounter
import com.example.cavesofzircon.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType

interface ZirconHolder : EntityType

val GameEntity<ZirconHolder>.zirconCounter: ZirconCounter
    get() = findAttribute(ZirconCounter::class).get()