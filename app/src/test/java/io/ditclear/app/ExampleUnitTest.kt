package io.ditclear.app

import org.junit.Test
import java.lang.reflect.Proxy
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

    @Test
    fun test_numJewelsInStones() {
        print(numJewelsInStones("aAz", "aAAbbbbbbzzz"))
    }

    fun numJewelsInStones(J: String, S: String): Int {
        return S.toCharArray().filter { J.contains(it) }.size
    }

    @Test
    fun test_flipAndInvertImage() {
        val array = arrayOf(intArrayOf(1, 1, 0, 0), intArrayOf(1, 0, 0, 1), intArrayOf(0, 1, 1, 1), intArrayOf(1, 0, 1, 0))
//        print(flipAndInvertImage(array))

        flipAndInvertImage(array).forEach {
            it.forEach {
                print(it)
            }
            print("\n")
        }
    }

    fun flipAndInvertImage(A: Array<IntArray>): Array<IntArray> {
        return A.map { it.reversedArray().map { Math.abs(it - 1) }.toIntArray() }.toTypedArray()
    }

    @Test
    fun test_judgeCircle() {
        print(judgeCircle("LLRRU"))
    }

    fun judgeCircle(moves: String): Boolean {
        if (moves.length % 2 != 0) {
            return false
        }
        return moves.count { it == 'U' } == moves.count { it == 'D' } && moves.count { it == 'L' } == moves.count { it == 'R' }
    }

    @Test
    fun test_findComplement() {
        print(findComplement(5))
    }

    fun findComplement(num: Int) =
            num.toString(2).map { if (it == '1') 0 else 1 }.joinToString("").toInt(2)


    @Test
    fun test_hammingDistance() {
        print(hammingDistance(1, 4))
    }

    fun hammingDistance(x: Int, y: Int): Int {
        var xHex = x.toString(2)
        var yHex = y.toString(2)

        if (xHex.length != yHex.length) {
            if (xHex.length > yHex.length) {
                yHex = yHex.padStart(xHex.length, '0')
            } else {
                xHex = xHex.padStart(yHex.length, '0')
            }
        }
        return xHex.filterIndexed { index, c -> c != yHex[index] }.length

    }

    @Test
    fun test_selfDividingNumbers() {
        print(selfDividingNumbers(1, 22))
    }

    fun selfDividingNumbers(left: Int, right: Int): List<Int> {
        return (left..right).filter {
            it.toString().toCharArray().all { c: Char -> c != '0' && it % c.toString().toInt() == 0 }
        }.toList()
    }

    @Test
    fun test_mergeTrees() {

    }

    fun mergeTrees(t1: TreeNode?, t2: TreeNode?): TreeNode? {

        var root: TreeNode? = null
        var left_1: TreeNode? = null
        var left_2: TreeNode? = null
        var right_1: TreeNode? = null
        var right_2: TreeNode? = null

        if (t1 == null && t2 == null) {
            return null
        } else if (t1 == null) {
            return t2
        } else if (t2 == null) {
            return t1
        } else {
            root = TreeNode(t1.`val` + t2.`val`)
            left_1 = t1.left
            left_2 = t2.left
            right_1 = t1.right
            right_2 = t2.right
        }
        root.left = mergeTrees(left_1, left_2)
        root.right = mergeTrees(right_1, right_2)
        return root
    }

    class TreeNode(var `val`: Int = 0) {
        var left: TreeNode? = null
        var right: TreeNode? = null

        override fun toString(): String {
            return "${`val`},${left.toString()},${right.toString()}"
        }
    }

    @Test
    fun test_reverseString() {
        print(reverseString("hello"))
    }

    fun reverseString(s: String): String {
        return s.reversed()
    }


    @Test
    fun test_invertTree() {

    }

    fun invertTree(root: TreeNode?): TreeNode? {
        if (root == null) {
            return null
        }
        val newRoot = TreeNode(root.`val`)
        newRoot.left = invertTree(root.right)
        newRoot.right = invertTree(root.left)
        return newRoot
    }


    fun maxDepth(root: TreeNode?): Int {
        if (root == null) {
            return 0
        }
        val leftDepth = maxDepth(root.left) + 1
        val rightDepth = maxDepth(root.right) + 1
        return if (leftDepth > rightDepth) leftDepth else rightDepth
    }

    fun minDepth(root: TreeNode?): Int {
        return if (root == null) {
            0
        } else if (root.left == null) {
            minDepth(root.right) + 1
        } else if (root.right == null) {
            minDepth(root.left) + 1
        } else {
            val leftDepth = minDepth(root.left) + 1
            val rightDepth = minDepth(root.right) + 1
            if (leftDepth < rightDepth) leftDepth else rightDepth
        }
    }

    @Test
    fun test_findWords() {
     findWords(arrayOf("Hello", "Alaska", "Dad", "Peace")).forEach {
         print(it)
     }
    }

    fun findWords(words: Array<String>): Array<String> {
        val line1 = "QWERTYUIOP"
        val line2 = "ASDFGHJKL"
        val line3 = "ZXCVBNM"

        return words.filter {
            it.all { line1.contains(it.toUpperCase()) }
                    || it.all { line2.contains(it.toUpperCase()) }
                    || it.all { line3.contains(it.toUpperCase()) }
        }.toTypedArray()
    }


    @Test
    fun test_proxy(){

        val javar= Javar()
        val proxy = DynamicProxy(javar)

        val programmer:Programmer = Proxy.newProxyInstance(javar.javaClass.classLoader, arrayOf(Programmer::class.java),proxy) as Programmer

        programmer.code()

        programmer.debug()
    }
}