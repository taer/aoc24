package dayX

import Direction
import println2
import readInput

fun main() {

    fun part1(input: List<String>): Int {
        val map = input.map { it.map { it } }
        val rows = map.indices
        val cols = map.first().indices

        fun get(row: Int, col: Int, count: Int, direction: Direction): String {
            if(count == 0) return ""
            if (row !in rows || col !in cols) return ""
            val letter = map[row][col]
            return letter + get(row + direction.deltaRow, col + direction.deltaCol, count-1, direction)
        }
        var count = 0
        for(row in rows){
            for (col in cols){
                if (map[row][col] == 'X') {
                    if ("XMAS" == get(row, col, 4, Direction.E)) count++
                    if ("XMAS" == get(row, col, 4, Direction.W)) count++
                    if ("XMAS" == get(row, col, 4, Direction.S)) count++
                    if ("XMAS" == get(row, col, 4, Direction.N)) count++

                    if ("XMAS" == get(row, col, 4, Direction.SE)) count++
                    if ("XMAS" == get(row, col, 4, Direction.SW)) count++

                    if ("XMAS" == get(row, col, 4, Direction.NE)) count++
                    if ("XMAS" == get(row, col, 4, Direction.NW)) count++
                }
            }
        }


        return count
    }


    fun part2(input: List<String>): Int {
        val map = input.map { it.map { it } }
        val rows = map.indices
        val cols = map.first().indices

        fun getDir(row: Int, col: Int, dir: Direction): String {
            val newRow = row + dir.deltaRow
            val newCol = col + dir.deltaCol
            if (newRow !in rows || newCol !in cols) return ""
            val letter = map[newRow][newCol]
            return letter.toString()
        }

        var count = 0
        for (row in rows) {
            for (col in cols) {
                if (map[row][col] == 'A'){
                    var rightTop = false
                    var leftTop = false
                    if("S" == getDir(row, col, Direction.SE)){
                        if("M" == getDir(row, col,  Direction.NW))
                            leftTop = true
                    }
                    if("M" == getDir(row, col,  Direction.SE)){
                        if("S" == getDir(row, col,   Direction.NW))
                            leftTop = true
                    }
                    if("S" == getDir(row, col,   Direction.SW)){
                        if("M" == getDir(row, col,  Direction.NE))
                            rightTop=true
                    }
                    if("M" == getDir(row, col,  Direction.SW)){
                        if("S" == getDir(row, col,  Direction.NE))
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