import com.rrpictureproductions.chess.figures.Color
import com.rrpictureproductions.chess.figures.King
import com.rrpictureproductions.chess.figures.Knight
import com.rrpictureproductions.chess.figures.Queen
import com.rrpictureproductions.chess.toPosition
import org.junit.Test


class ChessPiecesTest {

    @Test
    fun testReachablePositionsKing() {
        val king = King(Color.WHITE)

        val actual = king.getReachablePositionsFrom("D4".toPosition())
        val expected = setOf("C3", "C4", "C5", "D3", "D5", "E3", "E4", "E5").map(String::toPosition).toSet()
        assertEquals(expected, actual)

        assertEquals(expected = king.getReachablePositionsFrom("H1".toPosition()),
                     actual = setOf("G1", "G2", "H2").map(String::toPosition).toSet())
    }

    @Test
    fun testReachablePositionsQueen() {
        val queen = Queen(Color.WHITE)

        val actual = queen.getReachablePositionsFrom("D4".toPosition())
        val file = setOf("D1", "D2", "D3", "D5", "D6", "D7", "D8")
        val rank = setOf("A4", "B4", "C4", "E4", "F4", "G4", "H4")
        val major = setOf("A1", "B2", "C3", "E5", "F6", "G7", "H8")
        val minor = setOf("A7", "B6", "C5", "E3", "F2", "G1")
        val expected = (file + rank + major + minor).map(String::toPosition).toSet()
        assertEquals(expected, actual)

        val actual2 = queen.getReachablePositionsFrom("H1".toPosition())
        val file2 = setOf("H2", "H3", "H4", "H5", "H6", "H7", "H8")
        val rank2 = setOf("A1", "B1", "C1", "D1", "E1", "F1", "G1")
        val minor2 = setOf("A8", "B7", "C6", "D5", "E4", "F3", "G2")
        val expected2 = (file2 + rank2 + minor2).map(String::toPosition).toSet()
        assertEquals(expected2, actual2)
    }

    @Test
    fun testReachablePositionsKnight() {
        val knight = Knight(Color.WHITE)

        val actual = knight.getReachablePositionsFrom("D4".toPosition())
        val expected = setOf("B3", "B5", "C2", "C6", "E2", "E6", "F3", "F5").map(String::toPosition).toSet()
        assertEquals(expected, actual)

        assertEquals(expected = knight.getReachablePositionsFrom("H1".toPosition()),
                     actual = setOf("F2", "G3").map(String::toPosition).toSet())
    }
}