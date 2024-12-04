package dayX

import println2
import readInput

fun main() {

    fun part1(input: List<String>): Int {
        return 1
    }

    fun part2(input: List<String>): Int {
        return 1

    }

    val testInput = readInput("Day0x_test")

    val part1 = part1(testInput)
    check(part1 == 161){part1}

    val input = readInput("Day0x")
    part1(input).println2()
    check(part1(input) == 170068701){part1}


    part2(input).println2()
    check(part2(input) == 78683433){part1}

}
