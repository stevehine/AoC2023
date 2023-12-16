package day16

import common.Vec2
import java.io.File
import java.util.*
import kotlin.time.measureTime

fun main() {
    val inputData = File("out/production/SteveH/day16Input").readLines()

    println(measureTime { println("Part 1: ${part1(inputData)}") }.toString())
    println(measureTime { println("Part 2: ${part2(inputData)}") }.toString())
}

fun part1(inputData: List<String>): Int {
    val mirrors = Mirrors(inputData)
    return (mirrors.traverseMirrors(LaserBeam(Vec2(0, 0), 'e')))
}


fun part2(inputData: List<String>): Int {
    val mirrors = Mirrors(inputData)
    val startingBeams = mutableListOf<LaserBeam>()
    for (x in inputData[0].indices) {
        startingBeams.add(LaserBeam(Vec2(x, 0), 's'))
        startingBeams.add(LaserBeam(Vec2(x, inputData.size - 1), 'n'))
    }
    for (y in inputData[0].indices) {
        startingBeams.add(LaserBeam(Vec2(0, y), 'e'))
        startingBeams.add(LaserBeam(Vec2(inputData[0].length - 1, y), 'w'))
    }

    return startingBeams.maxOfOrNull { mirrors.traverseMirrors(it) }!!
}

class LaserBeam(var location: Vec2, var direction: Char) {
    override fun equals(other: Any?): Boolean {
        return other is LaserBeam && other.location == this.location && other.direction == this.direction
    }

    override fun hashCode(): Int {
        var result = location.hashCode()
        result = 31 * result + direction.hashCode()
        return result
    }

    override fun toString(): String {
        return "${location.x}, ${location.y} ${
            when (direction) {
                'n' -> '^'
                's' -> 'v'
                'w' -> '<'
                else -> '>'
            }
        }"
    }
}

class Mirrors() {

    fun traverseMirrors(startPoint: LaserBeam): Int {
        val beamStack = Stack<LaserBeam>()
        beamStack.push(startPoint)
        val visited = mutableListOf<LaserBeam>()

        while (beamStack.isNotEmpty()) {
            val beam = beamStack.pop()
            if (!visited.contains(beam)) {
                visited.add(beam)
                beamStack.addAll(processBeam(beam))
            }
        }

        return visited.map { it.location }.distinct().count()
    }

    private fun processBeam(beam: LaserBeam): Collection<LaserBeam> {
        val options = mutableListOf<LaserBeam>()

        if (mirrorMap.containsKey(beam.location)) {
            options.addAll(interactions[mirrorMap[beam.location]]!![beam.direction]!!.map {
                nextLocation(
                    beam.location,
                    it
                )
            })
        } else {
            options.add(nextLocation(beam.location, beam.direction))
        }

        return options.filter { it.location.x >= 0 && it.location.y >= 0 && it.location.x < width && it.location.y < height }
    }

    private fun nextLocation(location: Vec2, direction: Char): LaserBeam {
        return LaserBeam(
            when (direction) {
                'n' -> Vec2(location.x, location.y - 1)
                's' -> Vec2(location.x, location.y + 1)
                'e' -> Vec2(location.x + 1, location.y)
                else -> Vec2(location.x - 1, location.y)
            }, direction
        )
    }

    private val interactions = mapOf(
        '|' to
                mapOf(
                    'n' to listOf('n'),
                    's' to listOf('s'),
                    'e' to listOf('n', 's'),
                    'w' to listOf('n', 's')
                ),
        '-' to
                mapOf(
                    'n' to listOf('e', 'w'),
                    's' to listOf('e', 'w'),
                    'e' to listOf('e'),
                    'w' to listOf('w')
                ),
        '\\' to
                mapOf(
                    'n' to listOf('w'),
                    's' to listOf('e'),
                    'e' to listOf('s'),
                    'w' to listOf('n')
                ),
        '/' to
                mapOf(
                    'n' to listOf('e'),
                    's' to listOf('w'),
                    'e' to listOf('n'),
                    'w' to listOf('s')
                )
    )

    private val mirrorMap = mutableMapOf<Vec2, Char>()
    private var width: Int = 0
    private var height: Int = 0

    constructor(inputData: List<String>) : this() {
        height = inputData.size
        width = inputData[0].length
        for (y in inputData.indices) {
            for (x in inputData[y].indices) {
                if (inputData[y][x] != '.')
                    mirrorMap[Vec2(x, y)] = inputData[y][x]
            }
        }

    }
}