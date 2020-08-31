import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    println(List(scanner.nextInt()) { scanner.nextInt() }.min())
}