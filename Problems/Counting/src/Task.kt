import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val input = IntArray(scanner.nextInt()) { scanner.nextInt() }

    val m = scanner.nextInt()

    // Thanks to Anton :)
    val sum = input.count { m == it }

    println(sum)
}