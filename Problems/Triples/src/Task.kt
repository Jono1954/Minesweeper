import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val size = scanner.nextInt()
    val array = IntArray(size)

    for (i in 0..array.lastIndex) {
        array[i] = scanner.nextInt()
    }

    var noOfTriples = 0

    for (i in 0..array.lastIndex - 2) {
        if (array[i] + 1 == array[i + 1] && array[i + 1] + 1 == array[i + 2]) noOfTriples++
    }
    println(noOfTriples)
}
