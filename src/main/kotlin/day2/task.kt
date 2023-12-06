package day2

import java.io.File

fun main() {
    val inputData = File("out/production/SteveH/day2Input").readLines()

    println("Part 1: ${part1(inputData)}")
    println("Part 2: ${part2(inputData)}")
}

fun part1(inputData: List<String>): Int {

    return inputData.map { row ->
        val parts = row.split(':').map { it.trim() }

        Game(parts[0].split(' ')[1].toInt(), parts[1])
    }.filter { game ->
        game.rounds.none {
            (it.containsKey("red") && it["red"]!! > 12)
                    || (it.containsKey("green") && it["green"]!! > 13)
                    || (it.containsKey("blue") && it["blue"]!! > 14)
        }
    }.sumOf { it.id }
}


fun part2(inputData: List<String>): Int {
    return inputData.map { row ->
        val parts = row.split(':').map { it.trim() }

        Game(parts[0].split(' ')[1].toInt(), parts[1])
    }.map { game ->
        val acc = mutableMapOf<String, Int>()
        game.rounds.forEach { round ->

            round.forEach {
                if (!acc.containsKey(it.key) || acc[it.key]!! < it.value)
                    acc[it.key] = it.value
            }

        }
        acc.map { it.value }
    }.sumOf { it.reduce { acc, i -> acc * i } }

}

class Game(val id: Int) {
    lateinit var rounds: List<Map<String, Int>>

    constructor(id: Int, gameData: String) : this(id) {
        val games = gameData.split(';')
        rounds = games.map { round ->
            val draw = round.split(", ").map { it.trim() }
            draw.associate { Pair(it.split(' ')[1], it.split(' ')[0].toInt()) }
        }
    }
}

