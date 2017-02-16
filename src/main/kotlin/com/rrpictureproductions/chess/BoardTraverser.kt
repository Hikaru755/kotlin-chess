package com.rrpictureproductions.chess

/**
 * Created by robin on 16.02.2017.
 */
fun Board.traverse(direction: Direction): (Position) -> Field? = { position ->
    position.move(direction.filesPerStep, direction.ranksPerStep)?.let { newPos ->
        this[newPos]
    }
}

enum class Direction(val filesPerStep: Int, val ranksPerStep: Int) {
    UP(0, 1),
    UPRIGHT(1, 1),
    RIGHT(1, 0),
    DOWNRIGHT(1, -1),
    DOWN(0, -1),
    DOWNLEFT(-1, -1),
    LEFT(-1, 0),
    UPLEFT(-1, 1)
}