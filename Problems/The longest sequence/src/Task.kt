fun main(args: Array<String>) {
    val n = readLine()!!
            .split(" ")
            .first()
            .toInt()

    val numbers = readLine()!!
            .split(" ")
            .map { it.toInt() }
            .toList()

    val longest = mutableListOf<Int>()

    var carryOn = true
    var index = 0
    var length = 1

    while (carryOn) {
        when {
            // one number
            numbers.size == 1 -> {
                longest.add(element = 1)
                carryOn = false
            }

            // two numbers
            numbers.size == 2 && numbers[1] >= numbers[0] -> {
                longest.add(element = 2)
                carryOn = false
            }

            // More than two numbers, increasing
            numbers[index + 1] >= numbers[index] -> {
                length++
            }

            // More than two numbers, decreasing
            numbers[index + 1] < numbers[index] -> {
                longest.add(element = length)
                length = 1
            }
        }
        index++
        if (index == numbers.lastIndex) {
            longest.add(length)
            carryOn = false
        }

    }
    if (longest.size > 0) println(longest.max()) else println(0)
}