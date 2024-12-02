package day01

import println2
import readInput
import kotlin.math.abs

fun main() {

    fun splitLists(input: List<String>): List<Pair<Int, Int>> {
        val xx = input.map {
            val split = it.split("\\s+".toRegex())

            split[0].toInt() to split[1].toInt()
        }
        return xx
    }

    fun part1(input: List<String>): Int {
        val xx = splitLists(input)
        val first = xx.map { it.first }.sorted()
        val second = xx.map { it.second }.sorted()
        return first.zip(second){ a, b ->
            abs(a-b)
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val xx = splitLists(input)
        val inits = xx.map { it.first }
        val targets = xx.map { it.second }

        return inits.sumOf { firstNum ->
            val asd = targets.count { it == firstNum }
            asd * firstNum
        }
    }

    val testInput = readInput("Day01_test")

    val part1 = part1(testInput)
    check(part1 == 11)

    val input = readInput("Day01")
    val part2 = part2(testInput)
    check(part2 == 31)

    part1(input).println2()
    part2(input).println2()
}
