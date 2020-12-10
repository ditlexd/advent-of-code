import java.io.File
import java.lang.Error

fun main() {
    val lines = File("src/main/resources/day8.txt").readLines()
    second(lines)
}

fun second(lines: List<String>) {
    lines.forEachIndexed { index, line ->
        val (cmd, value) = line.split(" ")
        when (cmd) {
            "jmp" -> {
                val copy = ArrayList(lines)
                copy[index] = "nop $value"
                first(copy)
            }
            "nop" -> {
                val copy = ArrayList(lines)
                copy[index] = "jmp $value"
                first(copy)
            }
        }
    }
}

private fun first(lines: List<String>) {
    val map = BooleanArray(lines.size)

    var acc = 0;
    var index = 0;

    try {
        while (!map[index]) {
            map[index] = true
            val (cmd, value) = lines[index].split(" ")
            when (cmd) {
                "acc" -> {
                    acc += value.toInt()
                    index++
                }
                "jmp" -> {
                    index += value.toInt()
                }
                else -> {
                    index++;
                }
            }
        }
    } catch (e: ArrayIndexOutOfBoundsException) {
        println(acc)
        return
    }
}


