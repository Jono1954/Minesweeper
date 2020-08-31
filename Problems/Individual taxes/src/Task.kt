import java.util.*
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val numOfCompanies = scanner.nextInt()

    val yearlyIncomes = IntArray(numOfCompanies) { scanner.nextInt() }

    val taxPercentage = IntArray(numOfCompanies) { scanner.nextInt() }

    val taxPaid = IntArray(numOfCompanies) { yearlyIncomes[it] * taxPercentage[it] }

    println(taxPaid.indexOf(taxPaid.max()!!) + 1)
}