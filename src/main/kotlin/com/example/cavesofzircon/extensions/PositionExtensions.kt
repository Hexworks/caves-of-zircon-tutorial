package com.example.cavesofzircon.extensions

import org.hexworks.zircon.api.data.Position3D

fun Position3D.sameLevelNeighborsShuffled(): List<Position3D> { // 1
    return (-1..1).flatMap { x ->
        (-1..1).map { y -> // 2
            this.withRelativeX(x).withRelativeY(y) // 3
        }
    }.minus(this).shuffled() // 4
}
