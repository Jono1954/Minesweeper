import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val a = List(scanner.nextInt()) { scanner.nextInt() }

    // Single line solution from Mihael Stormrage - very clever :)
    Collections.rotate(a, 1)

    println(a.joinToString(" "))
}
