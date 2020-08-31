import java.util.*
import java.util.stream.IntStream.range

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val (a, b) = scanner.nextLine()
            .split(" ")
            .map { it.toLong() }

    println((a until b)
            .fold(1L) { mul, item -> mul * item })

}