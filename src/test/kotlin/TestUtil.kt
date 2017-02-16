/**
 * Created by robin on 16.02.2017.
 */

fun <T, U> assertEquals(expected: T?, actual: U?) {
    assert(expected == actual) { "\nExpected:\n$expected\nWas:\n$actual" }
}