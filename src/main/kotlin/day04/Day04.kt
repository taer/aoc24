package dayX

import println2
import readInput

fun main() {

    fun part1(input: List<String>): Int {
        val map = input.map { it.map { it } }
        val rows = map.indices
        val cols = map.first().indices

        fun get(row: Int, col: Int, count: Int, dirX: Int, dirY:Int): String {
            if(count == 0) return ""
            if (row !in rows || col !in cols) return ""
            val letter = map[row][col]
            return letter + get(row + dirX, col + dirY, count-1, dirX, dirY)
        }
        var count = 0
        for(row in rows){
            for (col in cols){
                if("XMAS" == get(row, col, 4, 0, 1)) count++
                if("XMAS" == get(row, col, 4, 0, -1)) count++
                if("XMAS" == get(row, col, 4, 1, 0)) count++
                if("XMAS" == get(row, col, 4, -1, 0)) count++

                if("XMAS" == get(row, col, 4, 1, 1)) count++
                if("XMAS" == get(row, col, 4, -1, -1)) count++

                if("XMAS" == get(row, col, 4, -1, 1)) count++
                if("XMAS" == get(row, col, 4, 1, -1)) count++
            }
        }


        return count
    }

    fun part2(input: List<String>): Int {
        val map = input.map { it.map { it } }
        val rows = map.indices
        val cols = map.first().indices

        fun get(row: Int, col: Int, count: Int, dirX: Int, dirY:Int): String {
            if(count == 0) return ""
            if (row !in rows || col !in cols) return ""
            val letter = map[row][col]
            return letter + get(row + dirX, col + dirY, count-1, dirX, dirY)
        }

        var count = 0
        for (row in rows) {
            for (col in cols) {
                if (map[row][col] == 'A'){
                    var rightTop = false
                    var leftTop = false
                    if("AS" == get(row, col, 2, 1, 1)){
                        if("AM" == get(row, col, 2, -1, -1))
                            leftTop = true
                    }
                    if("AM" == get(row, col, 2, 1, 1)){
                        if("AS" == get(row, col, 2, -1, -1))
                            leftTop = true
                    }
                    if("AS" == get(row, col, 2, -1, 1)){
                        if("AM" == get(row, col, 2, 1, -1))
                            rightTop=true
                    }
                    if("AM" == get(row, col, 2, -1, 1)){
                        if("AS" == get(row, col, 2, 1, -1))
                            rightTop=true
                    }
                    if(leftTop&&rightTop) count++

                }
            }
        }
        return count
    }

    val testInput = readInput("Day04_test")

    val part1 = part1(testInput)
    check(part1 == 18){part1}

    val input = readInput("Day04")
    part1(input).println2()
    check(part1(input) == 2406){part1}
    val part2 = part2(testInput)
    check(part2 == 9){part2}

    part2(input).println2()
    check(part2(input) == 1807){part1}

}
