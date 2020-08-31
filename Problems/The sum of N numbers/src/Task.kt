import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val num = scanner.nextLine().toInt()

    var sum = 0

    repeat(num) { sum += scanner.nextInt() }

    println(sum)

    // (IntArray(scanner.nextInt()) { scanner.nextInt() }.sum())
    //     .apply { println() }
}
