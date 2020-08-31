import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    (scanner.nextInt()..scanner.nextInt()) // create a range
            .reduce { sum, integer -> sum + integer }
            .also { println(it) }
}
