import java.util.*

fun main(args: Array<String>) {

    // This solution thanks to Emilija Nuzhina-Amatya below
    // Uses strings in place of arrays

    readLine() // read in size of array but not used

    val numbers = readLine()!! // read in array of integers as a string
    val pair = readLine()!! // two numbers as a string

    println(if (numbers.contains(pair) || numbers.contains(pair.reversed())) "YES" else "NO")
}