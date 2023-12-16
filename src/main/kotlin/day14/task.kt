package day14

import common.Vec2
import java.io.File
import kotlin.time.measureTime

fun main() {
    val inputData = File("out/production/SteveH/day14Input").readLines()

    println(measureTime { println("Part 1: ${part1(inputData)}") }.toString())
    println(measureTime { println("Part 2: ${part2(inputData)}") }.toString())
}

fun part1(inputData: List<String>): Int {
    val rocks = createRockMap(inputData)

    rocks.groupBy { it.position.x }.forEach { rocKolumn ->
        rocKolumn.value.filter { it.round }.sortedBy { it.position.y }.forEach { rock ->
            var blockage = rocKolumn.value.lastOrNull { it.position.y < rock.position.y }
            rock.position.y = when (blockage) {
                null -> 0
                else -> blockage.position.y + 1
            }

        }
    }
    for (y in inputData.indices) {
        for (x in inputData[y].indices) {
            var rock = rocks.filter { it.position == Vec2(x, y) }.firstOrNull()
            print(
                "${
                    when (rock) {
                        null -> '.'
                        else -> when (rock.round) {
                            true -> 'O'
                            else -> '#'
                        }
                    }
                }"
            )
        }
        println()
    }
    return rocks.filter { it.round }.sumOf { inputData.size - it.position.y }
}


fun part2(inputData: List<String>): Int {
    val bottom = inputData.size - 1
    val right = inputData[0].length - 1
    val rocks = createRockMap(inputData)
    var layout = rocks.map { it }.sortedBy { it.position.y }.sortedBy { it.position.x }.joinToString { "$it|" }
    var load = rocks.filter { it.round }.sumOf { inputData.size - it.position.y }
    val layoutCache = mutableListOf<String>()
    val loadCache = mutableListOf<Int>()

    do {
        layoutCache.add(layout)
        loadCache.add(load)
        //North
        rocks.groupBy { it.position.x }.map { it.value.sortedBy { it.position.y } }.forEach { rocKolumn ->
            rocKolumn.filter { it.round }.forEach { rock ->
                var blockage = rocKolumn.lastOrNull { it.position.y < rock.position.y }
                rock.position.y = when (blockage) {
                    null -> 0
                    else -> blockage.position.y + 1
                }

            }
        }


        //West
        rocks.groupBy { it.position.y }.map { it.value.sortedBy { it.position.x } }.forEach { rockRow ->
            rockRow.filter { it.round }.forEach { rock ->
                var blockage = rockRow.lastOrNull { it.position.x < rock.position.x }
                rock.position.x = when (blockage) {
                    null -> 0
                    else -> blockage.position.x + 1
                }

            }
        }


        //South
        rocks.groupBy { it.position.x }.map { it.value.sortedByDescending { it.position.y } }.forEach { rocKolumn ->
            rocKolumn.filter { it.round }.forEach { rock ->
                var blockage = rocKolumn.lastOrNull { it.position.y > rock.position.y }
                rock.position.y = when (blockage) {
                    null -> bottom
                    else -> blockage.position.y - 1
                }

            }
        }

        //East

        rocks.groupBy { it.position.y }.map { it.value.sortedByDescending { it.position.x } }.forEach { rockRow ->
            rockRow.filter { it.round }.forEach { rock ->
                var blockage = rockRow.lastOrNull { it.position.x > rock.position.x }
                rock.position.x = when (blockage) {
                    null -> right
                    else -> blockage.position.x - 1
                }

            }
        }

        layout = rocks.map { it }.sortedBy { it.position.y }.sortedBy { it.position.x }.joinToString { "$it|" }
        load = rocks.filter { it.round }.sumOf { inputData.size - it.position.y }

    } while (!layoutCache.contains(layout))

    val match = layoutCache.indexOf(layout)
    val cycleSize = layoutCache.size - match
    return loadCache[match + (1000000000 - match) % cycleSize]


}

fun createRockMap(inputData: List<String>): List<Rock> {
    val rocks = mutableListOf<Rock>()
    for (y in inputData.indices) {
        for (x in inputData[y].indices) {
            when (inputData[y][x]) {
                '#' -> rocks.add(Rock(Vec2(x, y), false))
                'O' -> rocks.add(Rock(Vec2(x, y), true))
            }
        }
    }
    return rocks
}

class Rock(var position: Vec2, val round: Boolean) {
    override fun toString(): String {
        return "${position}, ${
            when (round) {
                true -> 'O'
                else -> '#'
            }
        }"
    }
}

