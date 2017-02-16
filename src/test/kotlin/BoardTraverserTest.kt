import com.rrpictureproductions.chess.*
import org.junit.Test

/**
 * Created by robin on 16.02.2017.
 */
class BoardTraverserTest {

    @Test
    fun testTraverseBoardSingleSteps() {
        val board = Board()
        val start = D5
        fun test(direction: Direction, expected: Position?) {
            val actual = board.traverse(direction)(start)
            assertEquals(expected, actual?.position)
        }
        test(Direction.UP, D6)
        test(Direction.UPRIGHT, E6)
        test(Direction.RIGHT, E5)
        test(Direction.DOWNRIGHT, E4)
        test(Direction.DOWN, D4)
        test(Direction.DOWNLEFT, C4)
        test(Direction.LEFT, C5)
        test(Direction.UPLEFT, C6)
    }
}