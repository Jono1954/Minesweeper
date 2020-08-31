import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val count = scanner.nextLine().toInt()
    val number = scanner.nextLine().split(" ").map { it.toInt() }

    var result = "YES"
    var i = 1

    while (result == "YES" && i < count) {
        if (number[i - 1] > number[i]) result = "NO"
        i++
    }

    println(result)
}
