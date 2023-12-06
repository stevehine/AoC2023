package day6

import java.io.File
import kotlin.time.measureTime

fun main() {
    val inputData = File("out/production/SteveH/day6Input").readLines()

    println(measureTime { println("Part 1: ${part1(inputData)}") }.toString())
    println(measureTime { println("Part 2: ${part2(inputData)}") }.toString())
}

val regex = Regex("""\d+""")

fun part1(inputData: List<String>): Int {
    val times = mutableListOf<Int>()
    val distances = mutableListOf<Int>()

    regex.findAll(inputData[0]).forEach {
        times.add(it.value.toInt())
    }
    regex.findAll(inputData[1]).forEach {
        distances.add(it.value.toInt())
    }
    var comboScore = 1
    for (i in times.indices) {
        var winningCombos = 0
        for (t in 0..times[i]) {
            if (t * (times[i] - t) > distances[i])
                winningCombos += 1
        }
        comboScore *= winningCombos
    }

    return comboScore
}


fun part2(inputData: List<String>): Long {
    val time = regex.find(inputData[0].replace(" ", ""))!!.value.toLong()
    val distance = regex.find(inputData[1].replace(" ", ""))!!.value.toLong()


    var winningCombos = 0L
    for (t in 0..time) {
        if (t * (time - t) > distance)
            winningCombos += 1
    }

    return winningCombos
}

