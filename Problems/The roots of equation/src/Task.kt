import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val (a, b, c, d) = IntArray(4).map { scanner.nextLine().toInt() }

    for (x in 0..1000) {
        if (a.times(x * x * x) + b.times(x * x) + c.times(x) + d == 0) println(x)
    }
}
