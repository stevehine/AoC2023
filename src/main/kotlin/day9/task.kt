package day9

import java.io.File
import kotlin.time.measureTime

fun main() {
    val inputData = File("out/production/SteveH/day9Input").readLines()

    println(measureTime { println("Part 1: ${part1(inputData)}") }.toString())
    println(measureTime { println("Part 2: ${part2(inputData)}") }.toString())
}

fun part1(inputData: List<String>): Long {
    val history = inputData.map { it.split(" ").map { it.toLong() }.toMutableList() }
    history.forEach {
        it.add(calculateNextValue(it))
    }
    return history.sumOf { it.last() }
}

fun calculateNextValue(values: List<Long>): Long {
    if (values.filter { it != 0L }.isEmpty())
        return 0

    return values.last() + calculateNextValue(createSubSet(values))
}

fun createSubSet(values: List<Long>): List<Long> {
    val subSet = mutableListOf<Long>()
    for (i in 1..<values.size) {
        subSet.add(values[i] - values[i - 1])
    }
    return subSet
}


fun part2(inputData: List<String>): Long {
    val history = inputData.map { it.split(" ").map { it.toLong() }.toMutableList() }
    history.forEach {
        it.add(calculatePreviousValue(it))
    }
    return history.sumOf { it.last() }
}

fun calculatePreviousValue(values: List<Long>): Long {

    if (values.filter { it != 0L }.isEmpty())
        return 0

    return values.first() - calculatePreviousValue(createSubSet(values))

}

