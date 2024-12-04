package day03

import println2
import readInput

fun main() {

    val multiRegex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()

    fun part1(input: List<String>): Int {
        val single = input.single()
        val matchEntire = multiRegex.findAll(single)
        return matchEntire.map {
            it.groupValues[1].toInt() * it.groupValues[2].toInt()
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val single = input.single()
        val splitOnDo = single.split("do()")
        val validInstructions = splitOnDo.map {
            it.split("don't()")[0]
        }
        return validInstructions.sumOf {
            val matchEntire = multiRegex.findAll(it)
            val map = matchEntire.map {
                it.groupValues[1].toInt() * it.groupValues[2].toInt()
            }
            map.sum()
        }
    }

    val testInput = readInput("Day03_test")
    val testInput2 = readInput("Day03_test2")

    val part1 = part1(testInput)
    check(part1 == 161){part1}

    val input = readInput("Day03")
    part1(input).println2()
    check(part1(input) == 170068701){part1}


    val part2 = part2(testInput2)
    check(part2 == 48){part2}

    part2(input).println2()
    check(part2(input) == 78683433){part1}

}
