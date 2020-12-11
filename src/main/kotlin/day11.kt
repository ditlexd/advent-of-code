import java.io.File
import java.lang.IndexOutOfBoundsException

var hasChanged = true
fun main() {
    val map = ArrayList<List<Char>>();
    File("src/main/resources/day11.txt").forEachLine { line ->
        map.add(line.toCharArray().toList())
    }


    var transformedMap = map.toList()
    while (hasChanged) {
        hasChanged = false
        transformedMap = transform(transformedMap, ::getCloseNeighbors, 4)
    }

    val firstResult = transformedMap.fold(0, ::getOccupied)
    println(firstResult)

    var secondTransformed = map.toList()
    hasChanged = true

    while (hasChanged) {
        hasChanged = false
        secondTransformed = transform(secondTransformed, ::getDistNeighbors, 5)
    }

    val secondResult = secondTransformed.fold(0, ::getOccupied)
    println(secondResult)


}

private fun getDistNeighbors(map: List<List<Char>>, i: Int, j: Int): Int {
    var n = 0
    n += getTop(map, i - 1, j)
    n += getRight(map, i, j + 1)
    n += getBottom(map, i + 1, j)
    n += getLeft(map, i, j - 1)

    n += getTopLeft(map, i - 1, j - 1)
    n += getTopRight(map, i - 1, j + 1)
    n += getBottomRight(map, i + 1, j + 1)
    n += getBottomLeft(map, i + 1, j - 1)
    return n
}

private fun getLeft(map: List<List<Char>>, i: Int, j: Int): Int {
    return when {
        j < 0 -> 0
        map[i][j] == '#' -> 1
        map[i][j] == 'L' -> 0
        else -> getLeft(map, i, j - 1)
    }
}

private fun getRight(map: List<List<Char>>, i: Int, j: Int): Int {
    return when {
        j > map[0].size - 1 -> 0
        map[i][j] == '#' -> 1
        map[i][j] == 'L' -> 0
        else -> getRight(map, i, j + 1)
    }
}

private fun getTop(map: List<List<Char>>, i: Int, j: Int): Int {
    return when {
        i < 0 -> 0
        map[i][j] == '#' -> 1
        map[i][j] == 'L' -> 0
        else -> getTop(map, i - 1, j)
    }
}

private fun getBottom(map: List<List<Char>>, i: Int, j: Int): Int {
    return when {
        i > map.size - 1 -> 0
        map[i][j] == '#' -> 1
        map[i][j] == 'L' -> 0
        else -> getBottom(map, i + 1, j)
    }
}


private fun getTopLeft(map: List<List<Char>>, i: Int, j: Int): Int {
    return when {
        i < 0 || j < 0 -> 0
        map[i][j] == '#' -> 1
        map[i][j] == 'L' -> 0
        else -> getTopLeft(map, i - 1, j - 1)
    }
}

private fun getTopRight(map: List<List<Char>>, i: Int, j: Int): Int {
    return when {
        i < 0 || j > map[0].size - 1 -> 0
        map[i][j] == '#' -> 1
        map[i][j] == 'L' -> 0
        else -> getTopRight(map, i - 1, j + 1)
    }
}

private fun getBottomLeft(map: List<List<Char>>, i: Int, j: Int): Int {
    return when {
        i > map.size - 1 || j < 0 -> 0
        map[i][j] == '#' -> 1
        map[i][j] == 'L' -> 0
        else -> getBottomLeft(map, i + 1, j - 1)

    }
}

private fun getBottomRight(map: List<List<Char>>, i: Int, j: Int): Int {
    return when {
        j > map[0].size - 1 || i > map.size - 1 -> 0
        map[i][j] == '#' -> 1
        map[i][j] == 'L' -> 0
        else -> getBottomRight(map, i + 1, j + 1)

    }
}


private fun getOccupied(a: Int, value: List<Char>): Int {
    return value.fold(a) { b, item ->
        if (item == '#')
            b + 1
        else b
    }
}


fun transform(map: List<List<Char>>, getNeighbors: (List<List<Char>>, Int, Int) -> Int, noToMove: Int): List<List<Char>> {
    return map.mapIndexed { i, line ->
        line.mapIndexed { j, char ->
            val neighbors = getNeighbors(map, i, j)
            when (char) {
                'L' ->
                    when (neighbors) {
                        0 -> {
                            hasChanged = true
                            '#'
                        }
                        else -> char
                    }
                '#' -> {
                    when (neighbors) {
                        in noToMove..8 -> {
                            hasChanged = true
                            'L'
                        }
                        else -> char
                    }
                }
                else -> char
            }
        }
    }

}


private fun getCloseNeighbors(map: List<List<Char>>, i: Int, j: Int): Int {
    val height = map.size
    val width = map[0].size
    var n = 0;

    if (i > 0) if (map[i - 1][j] == '#') n++
    if (i < height - 1) if (map[i + 1][j] == '#') n++
    if (j > 0) if (map[i][j - 1] == '#') n++
    if (j < width - 1) if (map[i][j + 1] == '#') n++

    if (i > 0 && j > 0) if (map[i - 1][j - 1] == '#') n++
    if (i < height - 1 && j > 0) if (map[i + 1][j - 1] == '#') n++
    if (i < height - 1 && j < width - 1) if (map[i + 1][j + 1] == '#') n++
    if (i > 0 && j < width - 1) if (map[i - 1][j + 1] == '#') n++

    return n
}
