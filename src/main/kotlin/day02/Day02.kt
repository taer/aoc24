package day02

import println2
import readInput
import splitWhiteToInt
import kotlin.math.abs

fun main() {


    fun checkLevel(report: List<Int>): Boolean {
        val descending = report.zipWithNext().all { it.first > it.second }
        val ascending = report.zipWithNext().all { it.first < it.second }
        return if (!(descending || ascending)) {
            false
        } else {
            report.zipWithNext().all { abs(it.first - it.second) <= 3 }
        }
    }

    fun part1(input: List<String>): Int {
        return input.count { reportIn ->
            val report = reportIn.splitWhiteToInt()
            checkLevel(report)
        }

    }

    fun part2(input: List<String>): Int {
        return input.count { reportIn ->
            val report = reportIn.splitWhiteToInt()
            val origLevel = checkLevel(report)
            if (origLevel) {
                true
            } else {
                report.indices.any {
                    val modded = report.toMutableList()
                    modded.removeAt(it)
                    checkLevel(modded)
                }
            }
        }
    }

    val testInput = readInput("Day02_test")

    val part1 = part1(testInput)
    check(part1 == 2)

    val input = readInput("Day02")
    part1(input).println2()

    val part2 = part2(testInput)
    check(part2 == 4)

    part2(input).println2()
}
