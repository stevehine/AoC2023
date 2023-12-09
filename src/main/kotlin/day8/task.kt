package day8

import java.io.File
import java.util.*
import kotlin.time.measureTime

fun main() {
    val inputData = File("out/production/SteveH/day8Input").readLines()

    println(measureTime { println("Part 1: ${part1(inputData)}") }.toString())
    println(measureTime { println("Part 2: ${part2(inputData)}") }.toString())
}

fun part1(inputData: List<String>): Int {
    val directions = Stack<Char>()
    inputData[0].reversed().forEach { directions.add(it) }
    val nodes = mutableMapOf<String, Pair<String, String>>()
    for (i in 2..<inputData.size) {
        val sides = inputData[i].split(" = ")
        val pairs = sides[1].trim('(').trim(')').split(", ")
        nodes.put(sides[0], Pair(pairs[0], pairs[1]))
    }
    var node = nodes["AAA"]!!
    var steps = 0
    while (directions.isNotEmpty()) {
        steps += 1
        val nextNode = when (directions.pop()) {
            'L' -> node.first
            else -> node.second
        }
        if (nextNode == "ZZZ")
            return steps

        node = nodes[nextNode]!!
        if (directions.isEmpty())
            inputData[0].reversed().forEach { directions.add(it) }
    }
    return 0
}


fun part2(inputData: List<String>): Long {

    val nodes = mutableMapOf<String, Pair<String, String>>()
    for (i in 2..<inputData.size) {
        val sides = inputData[i].split(" = ")
        val pairs = sides[1].trim('(').trim(')').split(", ")
        nodes.put(sides[0], Pair(pairs[0], pairs[1]))
    }
    val nodeRepeats = nodes.keys.filter { it.endsWith("A") }
    val periods = mutableListOf<Long>()
    println(nodeRepeats.map { it })

    nodeRepeats.forEach {
        println("Searching for $it")
        var steps = 0L
        var index = 0
        val directions = Stack<Char>()
        inputData[0].reversed().forEach { directions.add(it) }
        var node = nodes[it]!!
        val matches = mutableMapOf<String, Long>()
        while (directions.isNotEmpty()) {
            steps += 1
            index += 1
            val nextNode = when (directions.pop()) {
                'L' -> node.first
                else -> node.second
            }
            if (nextNode.endsWith("Z")) {
                println("$nextNode in $steps")
                if (matches.keys.contains(nextNode)) {

                    periods.add(matches[nextNode]!!)
                    return@forEach
                }
                matches.put(nextNode, steps)
            }

            node = nodes[nextNode]!!
            if (directions.isEmpty()) {
                inputData[0].reversed().forEach { directions.add(it) }
                index = 0
            }
        }
    }

    for (p in periods.max()..periods.reduce { acc, l -> acc * l } step periods.max()) {
        if (periods.filter { p % it != 0L }.isEmpty())
            return p
    }
    return 0
}

