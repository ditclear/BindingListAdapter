package io.ditclear.app

import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {

        (0..10).mapNotNull { Random().nextInt(3) }.forEach {
            println(it)
        }
    }

}