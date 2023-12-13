package day10

import common.Vec2
import java.io.File
import java.util.*
import kotlin.time.measureTime

//map the cardinal directions from->to as movement directions
val pipeMap = mapOf(
    '|' to mapOf('n' to 'n', 's' to 's'),
    '-' to mapOf('e' to 'e', 'w' to 'w'),
    'L' to mapOf('s' to 'e', 'w' to 'n'),
    'J' to mapOf('e' to 'n', 's' to 'w'),
    '7' to mapOf('e' to 's', 'n' to 'w'),
    'F' to mapOf('w' to 's', 'n' to 'e'),
    '.' to emptyMap()
)

fun main() {
    val inputData = File("out/production/SteveH/day10Input").readLines()

    println(measureTime { println("Part 1: ${part1(inputData)}") }.toString())
    println(measureTime { println("Part 2: ${part2(inputData)}") }.toString())
}

fun locateStart(inputData: List<String>): Vec2? {
    for (y in inputData.indices) {
        if (inputData[y].contains('S'))
            return Vec2(inputData[y].indexOf('S'), y)
    }
    return null
}

fun part1(inputData: List<String>): Int {
    val startLocation = locateStart(inputData)!!

    return listOf(
        Pair(Vec2(startLocation.x - 1, startLocation.y), 'w'),
        Pair(Vec2(startLocation.x + 1, startLocation.y), 'e'),
        Pair(Vec2(startLocation.x, startLocation.y - 1), 's'),
        Pair(Vec2(startLocation.x, startLocation.y + 1), 'n')
    ).filter {
        it.first.x >= 0 && it.first.y >= 0 && it.first.x < inputData[0].length && it.first.y < inputData.size
    }.map {
        var steps = 1
        var location = it.first
        var direction = it.second
        while (location != startLocation) {
            steps += 1
            val pipe = inputData[location.y][location.x]
            if (!pipeMap[pipe]!!.containsKey(direction)) return@map 0
            pipeMap[pipe]!![direction]!!.also { newDirection -> direction = newDirection }
            location = when (direction) {
                'n' -> Vec2(location.x, location.y - 1)
                's' -> Vec2(location.x, location.y + 1)
                'e' -> Vec2(location.x + 1, location.y)
                else -> Vec2(location.x - 1, location.y)
            }
        }
        steps
    }.max() / 2


}


fun part2(inputData: List<String>): Int {
    val startLocation = locateStart(inputData)!!

    val pipeData = listOf(
        Pair(Vec2(startLocation.x - 1, startLocation.y), 'w'),
        Pair(Vec2(startLocation.x + 1, startLocation.y), 'e'),
        Pair(Vec2(startLocation.x, startLocation.y - 1), 's'),
        Pair(Vec2(startLocation.x, startLocation.y + 1), 'n')
    ).filter {
        it.first.x >= 0 && it.first.y >= 0 && it.first.x < inputData[0].length && it.first.y < inputData.size
    }.map { origin ->
        val route = mutableListOf(startLocation)
        var location = origin.first
        var direction = origin.second
        while (location != startLocation) {
            route.add(location)
            val pipe = inputData[location.y][location.x]
            if (!pipeMap[pipe]!!.containsKey(direction)) return@map Pair(emptyList<Vec2>(), ' ')
            direction = pipeMap[pipe]!![direction]!!
            location = when (direction) {
                'n' -> Vec2(location.x, location.y - 1)
                's' -> Vec2(location.x, location.y + 1)
                'e' -> Vec2(location.x + 1, location.y)
                else -> Vec2(location.x - 1, location.y)
            }
        }
        val startPiece = pipeMap.keys.first {
            pipeMap[it]!!.keys.contains(direction) && pipeMap[it]!![direction]!! == origin.second
        }
        Pair(route, startPiece)
    }.maxByOrNull { it.first.size }!!

    val pipe = pipeData.first
    val startPiece = pipeData.second


//    // We have the pipe; now let's turn it into a giant pipe!
    val bigPipe = mutableListOf<String>()
    for (y in inputData.indices) {
        bigPipe.addAll(listOf("", "", ""))
        for (x in inputData[y].indices) {
            if (!pipe.contains(Vec2(x, y))) {
                bigPipe[y * 3] += "..."
                bigPipe[y * 3 + 1] += "..."
                bigPipe[y * 3 + 2] += "..."
            } else {
                val piece: Char = when (inputData[y][x]) {
                    'S' -> startPiece
                    else -> inputData[y][x]
                }
                when (piece) {
                    '|' -> {
                        bigPipe[y * 3] += ".X."
                        bigPipe[y * 3 + 1] += ".X."
                        bigPipe[y * 3 + 2] += ".X."
                    }

                    '-' -> {
                        bigPipe[y * 3] += "..."
                        bigPipe[y * 3 + 1] += "XXX"
                        bigPipe[y * 3 + 2] += "..."
                    }

                    'L' -> {
                        bigPipe[y * 3] += ".X."
                        bigPipe[y * 3 + 1] += ".XX"
                        bigPipe[y * 3 + 2] += "..."
                    }

                    'J' -> {
                        bigPipe[y * 3] += ".X."
                        bigPipe[y * 3 + 1] += "XX."
                        bigPipe[y * 3 + 2] += "..."
                    }

                    '7' -> {
                        bigPipe[y * 3] += "..."
                        bigPipe[y * 3 + 1] += "XX."
                        bigPipe[y * 3 + 2] += ".X."
                    }

                    'F' -> {
                        bigPipe[y * 3] += "..."
                        bigPipe[y * 3 + 1] += ".XX"
                        bigPipe[y * 3 + 2] += ".X."
                    }
                }
            }

        }
    }

    val fillStack = Stack<Vec2>()
    val visited = mutableListOf<Vec2>()
    fillStack.add(Vec2(0, 0))
    while (fillStack.isNotEmpty()) {
        val location = fillStack.pop()
        visited.add(location)
        if (bigPipe[location.y][location.x] == '.') {
            bigPipe[location.y] = bigPipe[location.y].replaceRange(location.x..location.x, " ")
            listOf(
                Vec2(location.x - 1, location.y),
                Vec2(location.x + 1, location.y),
                Vec2(location.x, location.y - 1),
                Vec2(location.x, location.y + 1)
            ).filter {
                it.x >= 0 && it.y >= 0 && it.x < bigPipe[0].length && it.y < bigPipe.size && !visited.contains(it)
            }.forEach { fillStack.add(it) }
        }
    }

    return inputData.indices.sumOf { y ->
        inputData[0].indices.map { x ->
            if (bigPipe[y * 3].substring(x * 3..x * 3 + 2).contains(' ') ||
                bigPipe[y * 3 + 1].substring(x * 3..x * 3 + 2).contains(' ') ||
                bigPipe[y * 3 + 2].substring(x * 3..x * 3 + 2).contains(' ')
            )
                0
            else
                1

        }.sum()
    }

}

