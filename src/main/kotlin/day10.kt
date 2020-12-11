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
    fun dfs(u: Int, t: Int) : Long {
        if (u == t) return 1
        if (memo[u] == 0L) {
            adj[u].forEach {neighbor ->
                memo[u] += dfs(neighbor, t)
            }
        }
        return memo[u]
    }

    return dfs(0, numbers.size-1)
}

private fun first(numbers: java.util.ArrayList<Int>): Int {
    var oneJolt = 0
    var threeJolt = 0

    for (i in 1 until numbers.size) {
        if (numbers[i] - 3 == numbers[i - 1]) threeJolt++
        if (numbers[i] - 1 == numbers[i - 1]) oneJolt++
    }
    return oneJolt * threeJolt
}
