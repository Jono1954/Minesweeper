import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val original = List(scanner.nextInt()) { scanner.nextInt() }

    Collections.rotate(original, scanner.nextInt())

    println(original.joinToString(" "))
}