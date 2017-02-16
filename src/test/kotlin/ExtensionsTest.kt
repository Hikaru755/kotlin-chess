import com.rrpictureproductions.chess.crossproduct
import kotlin.collections.asSequence as lazy
import org.junit.Test

/**
 * Created by robin on 16.02.2017.
 */
class ExtensionsTest {

    @Test
    fun testCrossProduct() {
        fun test(set1: Set<Any>, set2: Set<Any>, expect: Set<Any>) {
            val actual = set1.lazy().crossproduct(set2.lazy()).toSet()
            assertEquals(expect, actual)
        }

        test(set1 = setOf(1),
             set2 = setOf(2),
             expect = setOf(1 to 2))

        test(set1 = setOf(1, 2),
             set2 = setOf(3),
             expect = setOf(1 to 3, 2 to 3))

        test(set1 = setOf(1),
             set2 = setOf(2, 3),
             expect = setOf(1 to 2, 1 to 3))

        test(set1 = setOf(1, 2),
             set2 = setOf(3, 4),
             expect = setOf(1 to 3, 1 to 4, 2 to 3, 2 to 4))

    }

}