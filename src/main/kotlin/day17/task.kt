package day17

import common.Vec2
import java.io.File
import kotlin.properties.Delegates
import kotlin.time.measureTime

var width by Delegates.notNull<Int>()
var height by Delegates.notNull<Int>()


fun main() {
    val inputData = File("out/production/SteveH/day17Input").readLines()

//    println(measureTime { println("Part 1: ${part1(inputData)}") }.toString())
    println(measureTime { println("Part 2: ${part2(inputData)}") }.toString())
}

fun part1(inputData: List<String>): Int {
    height = inputData.size
    width = inputData[0].length

    return calculateRoute(inputData)
}

fun calculateRoute(inputData: List<String>, part2: Boolean = false): Int {
//    var valueMap = mutableListOf<MutableList<Int>>()
//    for(y in inputData.indices){
//        valueMap.add(mutableListOf())
//        for(x in inputData[y].indices) {
//            valueMap[x].add(inputData[y].substring(x..x).toInt())
//        }
//    }
    val reversemap = mapOf('n' to 's', 's' to 'n', 'w' to 'e', 'e' to 'w')
    var heatIndex = 0
    val path = mutableMapOf<Int, MutableList<RoutePoint>>()
    path.put(0, mutableListOf(RoutePoint(Vec2(0, 0), 0, '.', 0)))
    val visited = mutableListOf<String>()
    while (heatIndex <= path.keys.max()) {
        println("Checking path for score: $heatIndex")
        if (path.keys.contains(heatIndex))
            path[heatIndex]!!.forEach { nextLocation ->
                if (nextLocation.coordinate == Vec2(width - 1, height - 1)) {
                    if(!part2 || nextLocation.moveCount>=3)
                        return heatIndex
                }
                val x = nextLocation.coordinate.x
                val y = nextLocation.coordinate.y
                listOf(

                    Pair(Vec2(x, y - 1), 'n'),
                    Pair(Vec2(x - 1, y), 'w'),
                    Pair(Vec2(x + 1, y), 'e'),
                    Pair(Vec2(x, y + 1), 's')

                ).filter { reversemap[it.second] != nextLocation.direction }
                    .filter {
                        nextLocation.direction == '.' || (
                                (!part2 && (nextLocation.moveCount < 2 || nextLocation.direction != it.second))
                                        ||
                                        (part2 && ((nextLocation.direction == it.second && nextLocation.moveCount < 9)
                                                || (nextLocation.direction != it.second && nextLocation.moveCount >= 3))))
                    }.filter { it.first.x >= 0 && it.first.y >= 0 && it.first.x < width && it.first.y < height }.map {
                        RoutePoint(
                            it.first,
                            nextLocation.score + inputData[it.first.y].substring(it.first.x..it.first.x).toInt(),
                            it.second,
                            when (nextLocation.direction == it.second) {
                                true -> nextLocation.moveCount + 1
                                else -> 0
                            }
                        )
                    }.forEach { rp ->
                        path.putIfAbsent(rp.score, mutableListOf())
                        if (!visited.contains(rp.toString())) {
                            visited.add(rp.toString())

                            path[rp.score]!!.add(rp)
                        }
                    }
            }

        heatIndex += 1
    }
    return 0
}


fun part2(inputData: List<String>): Int {
    height = inputData.size
    width = inputData[0].length

    return calculateRoute(inputData, true)
    return 0
}

class RoutePoint(val coordinate: Vec2, val score: Int, val direction: Char, val moveCount: Int) {
    override fun equals(other: Any?) =
        other is RoutePoint && coordinate == other.coordinate && score == other.score && direction == other.direction && moveCount == other.moveCount

    override fun hashCode(): Int {
        var result = coordinate.hashCode()
        result = 31 * result + score
        result = 31 * result + direction.hashCode()
        result = 31 * result + moveCount
        return result
    }

    override fun toString(): String {
        return "${coordinate},$direction,$moveCount"
    }


}

//class RoutePoint2(val coordinate: Vec2, val score: Int, val direction: Char, val moveCount: Int) {
//    override fun equals(other: Any?) =
//        other is RoutePoint2 && coordinate == other.coordinate && score == other.score && direction == other.direction && moveCount == other.moveCount
//
//    override fun hashCode(): Int {
//        var result = coordinate.hashCode()
//        result = 31 * result + score
//        result = 31 * result + direction.hashCode()
//        return result
//    }
//
//    fun possibleMoves() {
//        var moves = mutableListOf<RoutePoint2>()
//        if (moveCount < 10) {
//            moves.add(
//                RoutePoint2(
//                    when (direction) {
//                        'n' ->
//                    }
//                )
//            )
//        }
//    }
//}

class OrderedStack() {
    val theStack = mutableListOf<RoutePoint>()

    fun add(rp: RoutePoint) = theStack.add(rp)

    fun addAll(rps: List<RoutePoint>) = theStack.addAll(rps)

    fun isNotEmpty() = theStack.isNotEmpty()

    fun pop(): RoutePoint {
        val result = theStack.sortedByDescending { it.coordinate.x + it.coordinate.y }.first()
        theStack.remove(result)
        return result
    }

    fun prune(lowestSoFar: Int) {
        theStack.removeIf { ((height - it.coordinate.y) + (width - it.coordinate.x) + it.score) >= lowestSoFar }
    }
}
