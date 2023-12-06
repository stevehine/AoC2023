package day4

import java.io.File
import java.util.*

fun main() {
    val inputData = File("out/production/SteveH/day4Input").readLines()

    println("Part 1: ${part1(inputData)}")
    println("Part 2: ${part2(inputData)}")
}

fun part1(inputData: List<String>): Int {
    return createScratchCards(inputData).sumOf { it.score }

}


fun part2(inputData: List<String>): Int {
    var totalCards = 0
    val cardStack = Stack<ScratchCard>()
    val cards = createScratchCards(inputData)
    cards.forEach { card ->
        cardStack.add(card)
    }

    while (cardStack.isNotEmpty()) {
        val card = cardStack.pop()
        totalCards++
        if (card.winningRange.first > 0)
            cardStack.addAll(
                cards.filter { it.id in card.winningRange }
            )

    }

    return totalCards
}

fun createScratchCards(inputData: List<String>): List<ScratchCard> {
    var id = 0
    return inputData.map { line ->
        val data = line.split(": ")[1].split(" | ")
        id += 1
        ScratchCard(
            id,
            data[0].trim().split(' ').filter { it != "" }.map { it.toInt() },
            data[1].trim().split(' ').filter { it != "" }.map { it.toInt() }
        )
    }
}

class ScratchCard(val id: Int) {
    var score = 0
    var winningRange = 0..0
    constructor(id:Int, mine:List<Int>, winning: List<Int> ) : this(id) {
        var count = 0
        mine.forEach{
            if(winning.contains(it)){
                count+=1
                if(score==0)
                    score=1
                else
                    score*=2
            }
        }
        if(count>0)
            winningRange = id + 1 .. id+count
    }
}
