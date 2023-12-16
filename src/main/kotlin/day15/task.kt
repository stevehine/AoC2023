package day15

import java.io.File
import kotlin.time.measureTime

fun main() {
    val inputData = File("out/production/SteveH/day15Input").readLines()

    println(measureTime { println("Part 1: ${part1(inputData)}") }.toString())
    println(measureTime { println("Part 2: ${part2(inputData)}") }.toString())
}

fun part1(inputData: List<String>): Int {
    return inputData[0].split(',').sumOf {
        var value = 0
        it.map { it.code }.forEach { value = ((value + it) * 17) % 256 }
        value
    }
}


fun part2(inputData: List<String>): Int {
    val lensBoxes = mutableMapOf<Int, MutableMap<String, Int>>()

    inputData[0].split(',').forEach {
        val sep = if (it.contains('-')) '-' else '='
        val lensDetails = it.split(sep)
        var boxNo = 0
        lensDetails[0].map { it.code }.forEach { boxNo = ((boxNo + it) * 17) % 256 }
        lensBoxes.putIfAbsent(boxNo, mutableMapOf())

        if (sep == '-') {
            lensBoxes[boxNo]!!.remove(lensDetails[0])
        } else {
            lensBoxes[boxNo]!!.putIfAbsent(lensDetails[0], 0)
            lensBoxes[boxNo]!![lensDetails[0]] = lensDetails[1].toInt()

        }
    }

    return lensBoxes.map { lensBox ->
        lensBox.value.map { it.value }.mapIndexed { index, power ->
            (lensBox.key+1) * (index + 1) * power
        }.sum()
    }.sum()
}

