package com.rrpictureproductions.chess

import com.rrpictureproductions.chess.Field
import com.rrpictureproductions.chess.figures.*

sealed class Action {

    abstract fun checkIsValidOn(board: Board)

    fun executeOn(board: Board) {
        checkIsValidOn(board)
        doExecuteOn(board)
    }
    protected abstract fun doExecuteOn(board: Board)

    class Reset : Action() {
        override fun checkIsValidOn(board: Board) = Unit

        override fun doExecuteOn(board: Board) = board.apply {
            files.values.flatMap { it.values }
                    .map { it.apply { piece = null } }
                    .filter { field -> field.rank in Board.STARTING_RANKS }
                    .forEach { field ->
                        val (file, rank) = field
                        val color = when (rank) {
                            in Board.WHITE_RANKS -> Color.WHITE
                            in Board.BLACK_RANKS -> Color.BLACK
                            else -> throw IllegalArgumentException("Invalid starting rank: $rank")
                        }
                        field.piece = if (rank in Board.PAWN_RANKS) {
                            Pawn(color)
                        } else when (file) {
                            Board.QUEEN_FILE -> Queen(color)
                            Board.KING_FILE -> King(color)
                            in Board.ROOK_FILES -> Rook(color)
                            in Board.KNIGHT_FILES -> Knight(color)
                            in Board.BISHOP_FILES -> Bishop(color)
                            else -> throw IndexOutOfBoundsException()
                        }
                    }
        }.discard
    }

    class Move(val from: Position, val to: Position) : Action() {
        override fun checkIsValidOn(board: Board) {
            val fromField = board[from]
            val toField = board[to]
            val movingPiece = fromField.piece ?: throw InvalidMoveException("There is no piece on $from")
            val reachablePositions = movingPiece.getReachablePositionsFrom(fromField.position)
            if(toField.position !in reachablePositions) throw InvalidMoveException("This move is not possible with this piece")
            val capturedPiece = toField.piece
            if(movingPiece.color == capturedPiece?.color) throw InvalidMoveException("There is already one of your pieces on $to")
            if(!movingPiece.canJump) {
                checkPath(board, fromField, toField)
            }
        }

        private fun checkPath(board: Board, fromField: Field, toField: Field) {
            val nextPosition = getStepsIterator(from, to)
            var next = nextPosition(fromField.position)
            while (next != toField.position) {
                if (board[next].piece != null) {
                    throw InvalidMoveException("There is a piece in the way on $next")
                }
                next = nextPosition(next)
            }
        }

        override fun doExecuteOn(board: Board) {
            val startField = board[from]
            val endField = board[to]
            val piece = startField.piece ?: throw InvalidMoveException("No piece on field $from")
            println("Moving from $from to $to")
            startField.piece = null
            endField.piece = piece
        }

    }
}

fun getPathType(from: Position, to: Position): PathType {
    val fileDistance = to.file - from.file
    val rankDistance = to.rank - from.rank
    val fileStep = when {
        fileDistance < 0 -> -1
        fileDistance > 0 -> 1
        else -> 0
    }
    val rankStep = when {
        rankDistance < 0 -> -1
        rankDistance > 0 -> 1
        else -> 0
    }
    return PathType(fileStep, rankStep)
}

fun getStepsIterator(from: Position, to: Position): (Position) -> Position {
    val pathType = getPathType(from, to)
    return { it.move(pathType.fileStep, pathType.rankStep) ?: throw RuntimeException("This should not happen") }
}

class PathType(val fileStep: Int, val rankStep: Int)

sealed class RecordedAction(val originalAction: Action) {
    fun replayOn(board: Board) = originalAction.executeOn(board)
    abstract fun undoOn(board: Board)
}

class InvalidMoveException(message: String) : Exception(message) {
    constructor(move: Action.Move) : this("Invalid move: $move")
}