import java.io.File

fun main() {
    val numbers = ArrayList<Long>();
    File("src/main/resources/day9.txt").forEachLine { line ->
        numbers.add(line.toLong())
    }
    second(numbers, 1721308972)
}

private fun second(numbers: java.util.ArrayList<Long>, num: Long) {
    var back = 0;
    var front = 1;
    var sum = numbers[back] + numbers[front]

    while (sum != num && front < numbers.size) {
        if (sum > num) {
            sum -= numbers[back]
            back++
        }
        if (sum < num) {
            front++
            sum += numbers[front]
        }
    }

    val arr = numbers.subList(back, front)
    println(arr.min()!! + arr.max()!!)

}

private fun first(numbers: java.util.ArrayList<Long>) {
    var invalid = false;
    var result: Long = 0
    for (i in 25 until numbers.size) {
        if (invalid) break;

        val preamble = numbers.subList(i - 25, i)
        val sum = numbers[i]

        val set = HashSet<Long>()

        for (num in preamble) {
            set.add(num)
        }

        for (num in preamble) {
            val valueToFind = sum - num
            if (set.contains(valueToFind) && valueToFind != num) {
                invalid = false
                break
            } else {
                invalid = true
                result = sum
            }
        }
    }
    println(result)
}
