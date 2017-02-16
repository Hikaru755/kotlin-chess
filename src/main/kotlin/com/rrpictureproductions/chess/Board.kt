package com.rrpictureproductions.chess

import com.rrpictureproductions.chess.Board.File.*
import kotlin.collections.asSequence as lazy

class Board {

    val files: Map<File, Map<Int, Field>> = File.values().associate { file -> file to RANKS.associate { it to Field(file, it) } }
    val ranks: Map<Int, Map<File, Field>> = RANKS.associate { rank -> rank to FILES.associate { it to files[it, rank]!! } }

    operator fun get(position: Position) = files[position.file, position.rank]!!
    operator fun get(file: File, rank: Int) = files[file, rank]

    companion object {
        val FILES = File.values()
        val RANKS = 1..8
        val MAX_RANK = 8

        val ROOK_FILES = setOf(A, H)
        val KNIGHT_FILES = setOf(B, G)
        val BISHOP_FILES = setOf(C, F)
        val QUEEN_FILE = D
        val KING_FILE = E

        val PAWN_RANKS = setOf(2, 7)
        val WHITE_RANKS = setOf(1, 2)
        val BLACK_RANKS = setOf(7, 8)
        val STARTING_RANKS = WHITE_RANKS + BLACK_RANKS

        fun getPositionsOnFile(file: Board.File) =
                Board.RANKS.map { rank -> Position(file, rank) }.toSet()
        fun getPositionsOnRank(rank: Int) =
                Board.FILES.map { file -> Position(file, rank) }.toSet()
        fun getAdjacentPositions(position: Position) =
                (-1..1).lazy().crossproduct((-1..1).lazy())
                        .filter { it != 0 to 0 }
                        .map { position.move(it.first, it.second) }
                        .filterNotNull()
                        .toSet()
        fun getLeftDownDiagonalFrom(position: Position): Set<Position> {
            val ld = setOf(position.distanceToLeft(), position.distanceToBottom()).min()!!
            return (1..ld).map { position.move(-it, -it) }.filterNotNull().toSet()
        }
        fun getLeftUpDiagonalFrom(position: Position): Set<Position> {
            val ld = setOf(position.distanceToLeft(), position.distanceToTop()).min()!!
            return (1..ld).map { position.move(-it, it) }.filterNotNull().toSet()
        }
        fun getRightDownDiagonalFrom(position: Position): Set<Position> {
            val ld = setOf(position.distanceToRight(), position.distanceToBottom()).min()!!
            return (1..ld).map { position.move(it, -it) }.filterNotNull().toSet()
        }
        fun getRightUpDiagonalFrom(position: Position): Set<Position> {
            val ld = setOf(position.distanceToRight(), position.distanceToTop()).min()!!
            return (1..ld)
                    .map { position.move(it, it) }
                    .filterNotNull().toSet()
        }
        fun getDiagonalPositionsFrom(position: Position) =
                getLeftDownDiagonalFrom(position) +
                getLeftUpDiagonalFrom(position) +
                getRightDownDiagonalFrom(position) +
                getRightUpDiagonalFrom(position)
    }

    enum class File {
        A, B, C, D, E, F, G, H;

        operator fun minus(other: File) = ordinal - other.ordinal
        operator fun plus(distance: Int) = File.values()[ordinal + distance]

        companion object {
            val maxOrdinal = 7
        }
    }
}