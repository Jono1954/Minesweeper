import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val n = scanner.nextLine().split(" ").first().toInt()

    val numbers = IntArray(n) { scanner.nextInt() }

    // Sort largest to smallest
    val sorted = numbers.mySort()
    // val sorted = numbers.sortedDescending()

    val product = when (n) {
        1 -> println(sorted[0])
        in 2..n -> println(sorted[0] * sorted[1])
        else -> println("Error")
    }
}

fun IntArray.mySort(){
    return this.sortDescending()
}

