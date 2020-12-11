import java.io.File

fun main() {
    val numbers = ArrayList<Int>();
    File("src/main/resources/day10.txt").forEachLine { line ->
        numbers.add(line.toInt())
    }

    numbers.sort()
    numbers.add(0, 0)
    numbers.add(numbers.size, numbers.last() + 3)

    val firstResult = first(ArrayList(numbers))
    println(firstResult)

    val secondResult = second(ArrayList(numbers.map { it.toLong() }))
    println(secondResult)
}

private fun second(numbers: java.util.ArrayList<Long>): Long {
    val adj = numbers.mapIndexed { index, value ->
        fun dive(i: Int, acc: IntArray): IntArray {
            if (i == numbers.size) return acc
            return if (numbers[i] in value..value + 3) dive(i + 1, acc + intArrayOf(i))
            else acc
        }

        dive(index + 1, IntArray(0))
    }

    val memo = LongArray(numbers.size)
    fun dfs(u: Int, t: Int): Long {
        if (u == t) return 1
        if (memo[u] == 0L) {
            adj[u].forEach { neighbor ->
                memo[u] += dfs(neighbor, t)
            }
        }
        return memo[u]
    }

    return dfs(0, numbers.size - 1)
}

private class JoltFinder(val threes: Int, val ones: Int, val prev: Int)
private fun first(numbers: java.util.ArrayList<Int>): Int {
    val first = numbers.first()

    val res: JoltFinder = numbers
            .takeLast(numbers.size - 1)
            .fold(JoltFinder(0, 0, first)) { acc, value ->
                when (acc.prev) {
                    value - 3 -> JoltFinder(acc.threes + 1, acc.ones, value)
                    value - 1 -> JoltFinder(acc.threes, acc.ones + 1, value)
                    else -> JoltFinder(acc.threes, acc.ones, value)
                }
            }
    return res.threes * res.ones
}
