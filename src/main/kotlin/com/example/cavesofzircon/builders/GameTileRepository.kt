package com.example.cavesofzircon.builders

import com.example.cavesofzircon.builders.GameColors.ACCENT_COLOR
import com.example.cavesofzircon.builders.GameColors.FLOOR_BACKGROUND
import com.example.cavesofzircon.builders.GameColors.FLOOR_FOREGROUND
import com.example.cavesofzircon.builders.GameColors.WALL_BACKGROUND
import com.example.cavesofzircon.builders.GameColors.WALL_FOREGROUND
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols

object GameTileRepository {

    val EMPTY: CharacterTile = Tile.empty()

    val FLOOR: CharacterTile = Tile.newBuilder()
        .withCharacter(Symbols.INTERPUNCT)
        .withForegroundColor(FLOOR_FOREGROUND)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()

    val WALL: CharacterTile = Tile.newBuilder()
        .withCharacter('#')
        .withForegroundColor(WALL_FOREGROUND)
        .withBackgroundColor(WALL_BACKGROUND)
        .buildCharacterTile()

    val PLAYER = Tile.newBuilder()
        .withCharacter('@')
        .withBackgroundColor(FLOOR_BACKGROUND)
        .withForegroundColor(ACCENT_COLOR)
        .buildCharacterTile()

    val FUNGUS = Tile.newBuilder()
        .withCharacter('f')
        .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
        .withForegroundColor(GameColors.FUNGUS_COLOR)
        .buildCharacterTile()

    val STAIRS_UP = Tile.newBuilder()
        .withCharacter('<')
        .withForegroundColor(GameColors.ACCENT_COLOR)
        .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
        .buildCharacterTile()

    val STAIRS_DOWN = Tile.newBuilder()
        .withCharacter('>')
        .withForegroundColor(GameColors.ACCENT_COLOR)
        .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
        .buildCharacterTile()

    val UNREVEALED = Tile.newBuilder()
        .withCharacter(' ')
        .withBackgroundColor(GameColors.UNREVEALED_COLOR)
        .buildCharacterTile()
}
