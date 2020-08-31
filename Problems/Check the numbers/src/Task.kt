import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    // Solution below from lazy_void - thanks :)
    val size = scanner.nextLine()

    val array = scanner.nextLine().split(" ")

    val (n, m) = scanner.nextLine().split(" ")

    array
            .zipWithNext { a, b ->
                a == n && b == m || a == m && b == n
            }
            .firstOrNull { it }
            .apply {
                if (this == null) println("YES") else println("NO")
            }
}