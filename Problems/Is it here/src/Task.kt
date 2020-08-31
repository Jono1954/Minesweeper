import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val firstLine = scanner.nextLine()
            .split(" ")
            .first()
            .toInt()

    val secondLine = scanner.nextLine()
            .split(" ")
            .map { it.toInt() }

    val thirdLine = scanner.nextLine()
            .split(" ")
            .first()
            .toInt()

    println(if (secondLine.contains(thirdLine)) "YES" else "NO")

}