package day7

import java.io.File
import kotlin.time.measureTime

fun main() {
    val inputData = File("out/production/SteveH/day7Input").readLines()

    println(measureTime { println("Part 1: ${part1(inputData)}") }.toString())
    println(measureTime { println("Part 2: ${part2(inputData)}") }.toString())
}

fun part1(inputData: List<String>): Int {
    return evaluateHands(inputData)
}


fun part2(inputData: List<String>): Int {
    return evaluateHands(inputData, jokers = true)
}

fun evaluateHands(inputData: List<String>, jokers: Boolean = false): Int {
    val hands = inputData.map { Hand(it, jokers) }.sortedWith(handComparable)
    return hands.mapIndexed { index, hand ->
        (index + 1) * hand.bid
    }.sum()
}

val cardMap = mapOf(
    "2" to 2,
    "3" to 3,
    "4" to 4,
    "5" to 5,
    "6" to 6,
    "7" to 7,
    "8" to 8,
    "9" to 9,
    "T" to 10,
    "J" to 11,
    "Q" to 12,
    "K" to 13,
    "A" to 14
)

val handComparable = Comparator<Hand> { a, b ->
    when {
        a.score > b.score -> 1
        a.score < b.score -> -1
        else -> {
            var result = 0
            for (i in 0..4) {
                if (result == 0) {
                    if (a.hand[i] > b.hand[i])
                        result = 1
                    if (a.hand[i] < b.hand[i])
                        result = -1
                }

            }
            result
        }
    }
}

class Hand() {
    var bid = 0
    val hand = mutableListOf<Int>()

    var score = 0

    constructor(data: String, jokers: Boolean = false) : this() {
        val pieces = data.split(" ")
        bid = pieces[1].toInt()
        pieces[0].indices.forEach { i ->
            hand.add(cardMap[pieces[0].substring(i..i)]!!)
        }
        if (jokers) {
            val jokerHand = hand.map { if (it == 11) 1 else it }
            hand.clear()
            hand.addAll(jokerHand)
        }
        val handGrouping = hand.groupingBy { it }.eachCount()
        score = when (handGrouping.keys.size) {
            1 -> 7
            2 -> {
                if (jokers && handGrouping.keys.contains(1)) {
                    7
                } else {
                    if (handGrouping.values.contains(4))
                        6
                    else
                        5
                }
            }

            3 -> {
                if (handGrouping.values.contains(3)) {
                    // three of a kind
                    if (jokers && handGrouping.keys.contains(1))
                        6
                    else
                        4
                } else {
                    //two pair
                    if (jokers && handGrouping.keys.contains(1))
                        if (handGrouping[1] == 2)
                            6
                        else
                            5
                    else
                        3
                }
            }

            4 -> {
                if (jokers && handGrouping.keys.contains(1)) {
                    4
                } else
                    2
            }

            else -> {
                if (jokers && handGrouping.keys.contains(1))
                    2
                else
                    1
            }
        }
    }


}
