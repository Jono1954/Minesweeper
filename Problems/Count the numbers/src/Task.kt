import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val (a, b, n) = scanner.nextLine()
            .split(" ")
            .map { it.toInt() }

    println((a..b).filter { it % n == 0 }.size)
}
