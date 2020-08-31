fun main() {
    val longs = LongArray(3) { it + 100_000_000_001 }

    println(longs.joinToString())
}
