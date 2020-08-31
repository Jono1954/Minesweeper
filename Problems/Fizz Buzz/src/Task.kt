import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val (a, b) = scanner.nextLine()
            .split(" ")
            .map { it.toInt() }

    for (num in a..b) {
        when {
            num % 15 == 0 -> println("FizzBuzz")
            num % 3 == 0 -> println("Fizz")
            num % 5 == 0 -> println("Buzz")
            else -> println(num)
        }
    }
}
