import com.rrpictureproductions.chess.figures.Color
import com.rrpictureproductions.chess.figures.King
import com.rrpictureproductions.chess.figures.Knight
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
    fun testReachablePositionsKnight() {
        val knight = Knight(Color.WHITE)

        val actual = knight.getReachablePositionsFrom("D4".toPosition())
        val expected = setOf("B3", "B5", "C2", "C6", "E2", "E6", "F3", "F5").map(String::toPosition).toSet()
        assertEquals(expected, actual)

        assertEquals(expected = knight.getReachablePositionsFrom("H1".toPosition()),
                     actual = setOf("F2", "G3").map(String::toPosition).toSet())
    }
}