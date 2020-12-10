import java.io.File
import java.lang.IndexOutOfBoundsException

fun main() {
    val map = ArrayList<String>()
    val file = File("/Users/ditlef.diseth/hobby/kotlin-advent/src/main/resources/day3.txt")

    file.forEachLine {
        map.add(it.repeat(10000))
    }

    val first = getTreesInSlope(1, 1, map).toLong()
    val second = getTreesInSlope(3, 1, map).toLong()
    val third = getTreesInSlope(5, 1, map).toLong()
    val fourth = getTreesInSlope(7, 1, map).toLong()
    val fifth = getTreesInSlope(1, 2, map).toLong()

    println(first * second * third * fourth * fifth)

}

fun getTreesInSlope(right: Int, down: Int, map: ArrayList<String>): Int {
    var trees = 0;
    var i = 0;
    var j = 0;
    try {
        while (true) {
            if (map[i][j] == '#')
                trees++;
            i += down;
            j += right
        }
    } catch (error: IndexOutOfBoundsException) {
        return trees
    }
    return 0;
}

