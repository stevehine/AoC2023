package day1

import java.io.File
import kotlin.time.measureTime

fun main() {
    val inputData = File("out/production/SteveH/day1Input").readLines()

    println(measureTime { println("Part 1: ${part1(inputData)}") }.toString())
    println(measureTime { println("Part 2: ${part2(inputData)}") }.toString())
}

fun part1(inputData: List<String>): Int {
    return calculateSumOfCalibrationValues(inputData)
}


fun part2(inputData: List<String>): Int {
    return calculateSumOfCalibrationValues(inputData.map { replaceStringNumber(it) })
}

fun replaceStringNumber(input: String): String {
    val substitutions = mapOf(Pair("one", "1"), Pair("two", "2"), Pair("three", "3"), Pair("four", "4"), Pair("five", "5"), Pair("six", "6"), Pair("seven", "7"), Pair("eight", "8"), Pair("nine", "9"))
    var theString = input
    var matches = input.findAnyOf(substitutions.map { it.key })
    while (matches != null) {

        theString = theString.substring(0..<matches.first) + substitutions[matches.second] + theString.substring(matches.first + matches.second.length - 1)

        matches = theString.findAnyOf(substitutions.map { it.key })
    }

    return theString
}

fun calculateSumOfCalibrationValues(inputData: List<String>): Int {
    return inputData.map { line ->
        line.chars().filter { it >= '0'.code && it <= '9'.code }.map { it - '0'.code }.toArray()
    }.sumOf {
        it[0] * 10 + it.last()
    }
}