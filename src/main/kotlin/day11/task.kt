package day11

import common.Vec2
import java.io.File
import java.math.BigInteger
import kotlin.time.measureTime

fun main() {
    val inputData = File("out/production/SteveH/day11Input").readLines()

    println(measureTime { println("Part 1: ${part1(inputData)}") }.toString())
    println(measureTime { println("Part 2: ${part2(inputData)}") }.toString())
}

fun part1(inputData: List<String>): BigInteger {
    return scaleAndMeasure(inputData, 1)
}

fun scaleAndMeasure(inputData: List<String>, scale: Int): BigInteger {
    val yspaces = mutableListOf<Int>()
    val xspaces = mutableListOf<Int>()
    val galaxies = mutableListOf<Vec2>()
    yspaces.addAll(inputData.indices)
    xspaces.addAll(inputData[0].indices)
    for (y in inputData.indices) {
        for (x in inputData[y].indices) {
            if (inputData[y][x] == '#') {
                if (yspaces.contains(y))
                    yspaces.remove(y)
                if (xspaces.contains(x))
                    xspaces.remove(x)
                galaxies.add(Vec2(x, y))
            }
        }
    }
    val inflated = galaxies.map { galaxy ->
        Vec2BigInt(
            galaxy.x.toBigInteger() + (xspaces.count { it < galaxy.x }.toBigInteger() * scale.toBigInteger()),
            galaxy.y.toBigInteger() + (yspaces.count { it < galaxy.y }.toBigInteger() * scale.toBigInteger())
        )
    }

    var distance = BigInteger.ZERO
    for (i in inflated.indices) {
        for (j in i + 1..<inflated.size) {
            distance += (inflated[i].x - inflated[j].x).abs() + (inflated[i].y - inflated[j].y).abs()
        }
    }
    return distance
}


fun part2(inputData: List<String>, scale: Int = 999999): BigInteger {
    return scaleAndMeasure(inputData, scale)
}

class Vec2BigInt(val x: BigInteger, val y: BigInteger)