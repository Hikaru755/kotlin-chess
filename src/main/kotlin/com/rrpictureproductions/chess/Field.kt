package com.rrpictureproductions.chess

import com.rrpictureproductions.chess.Board
import com.rrpictureproductions.chess.figures.ChessPiece

data class Position(val file: Board.File, val rank: Int) {
    init {
        if(rank !in Board.RANKS) throw InvalidFieldException(this.toString())
    }
    fun move(files: Int, ranks: Int): Position? {
        if(file.ordinal + files !in Board.File.values().indices) return null
        if(rank + ranks !in Board.RANKS) return null
        return copy(file = file + files, rank = rank + ranks)
    }
    override fun toString() = "$file$rank"
}

fun Position.distanceToRight() = Board.File.maxOrdinal - file.ordinal
fun Position.distanceToLeft() = file.ordinal
fun Position.distanceToBottom() = rank - 1
fun Position.distanceToTop() = Board.MAX_RANK - rank

data class Field(val file: Board.File, val rank: Int, var piece: ChessPiece? = null) {
    val position = Position(file, rank)
    init {
        if(rank !in Board.RANKS) throw InvalidFieldException(this.toString())
    }

    override fun toString() = "$file$rank"
}

class InvalidFieldException : Exception {
    constructor(field: String) : super("Invalid field: $field")
    constructor(field: String, reason: Throwable) : super("Invalid field: $field", reason)
}