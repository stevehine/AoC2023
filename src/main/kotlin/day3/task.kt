package day3

import java.io.File
import kotlin.properties.Delegates

var width by Delegates.notNull<Int>()
var height by Delegates.notNull<Int>()

fun main() {
    val inputData = File("out/production/SteveH/day3Input").readLines()

    width = inputData[0].length
    height = inputData.size

    println("Part 1: ${part1(inputData)}")
    println("Part 2: ${part2(inputData)}")
}

val partNumberRegex = Regex("""\d+""")
val gearRegex = Regex("""\*""")

fun part1(inputData: List<String>): Int {

    val partNumbers = mutableListOf<PartNumber>()

    for (y in inputData.indices) {
        val matches = partNumberRegex.findAll(inputData[y])
        matches.forEach {
            partNumbers.add(PartNumber(it.range, y, it.value.toInt()))
        }
    }

    return partNumbers.filter { part ->
        part.generateAdjacents().any {
            when (inputData[it.y][it.x]) {
                '.', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> false
                else -> true
            }
        }
    }.sumOf { it.num }
}


fun part2(inputData: List<String>): Int {

    val partNumbers = mutableListOf<PartNumber>()
    val gears = mutableListOf<Vec2>()

    for (y in inputData.indices) {
        val partNumberMatches = partNumberRegex.findAll(inputData[y])
        partNumberMatches.forEach {
            partNumbers.add(PartNumber(it.range, y, it.value.toInt()))
        }
        val gearMatches = gearRegex.findAll(inputData[y])
        gearMatches.forEach {
            gears.add(Vec2(it.range.first, y))
        }
    }

    return gears.sumOf { gear ->
        val partsInRange = partNumbers.filter { part ->
            (gear.y - 1..gear.y + 1).contains(part.y) && part.generateAdjacents().contains(gear)
        }
        if (partsInRange.size == 2) partsInRange[0].num * partsInRange[1].num
        else 0

    }

}


class PartNumber(private val range: IntRange, val y: Int, val num: Int) {
    fun generateAdjacents(): List<Vec2> {
        val targets = mutableListOf<Vec2>()

        for (y in y - 1..y + 1) {
            targets.add(Vec2(range.first - 1, y))
            targets.add(Vec2(range.last + 1, y))
        }
        for (x in range) {
            targets.add(Vec2(x, y - 1))
            targets.add(Vec2(x, y + 1))
        }

        return targets.filter { it.x >= 0 && it.y >= 0 && it.x < width && it.y < height }
    }
}

class Vec2(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean = (other is Vec2) && other.x == x && other.y == y

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

}