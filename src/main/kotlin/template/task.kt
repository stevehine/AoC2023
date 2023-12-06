package template

import java.io.File
import kotlin.time.measureTime

fun main() {
    val inputData = File("out/production/SteveH/dayXInput").readLines()

    println(measureTime { println("Part 1: ${part1(inputData)}") }.toString())
    println(measureTime { println("Part 2: ${part2(inputData)}") }.toString())
}

fun part1(inputData: List<String>): Int {
    return 0
}


fun part2(inputData: List<String>): Int {
    return 0
}

