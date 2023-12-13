package day13

import common.Vec2
import java.io.File
import kotlin.time.measureTime

fun main() {
    val inputData = File("out/production/SteveH/day13Input").readLines()

    println(measureTime { println("Part 1: ${part1(inputData)}") }.toString())
    println(measureTime { println("Part 2: ${part2(inputData)}") }.toString())
}

fun part1(inputData: List<String>): Int {
    return findReflections(inputData, false)
}

fun part2(inputData: List<String>): Int {
    return findReflections(inputData, true)
}

fun findReflections(inputData: List<String>, smudged: Boolean = false): Int {
    val boards = mutableMapOf<Int, MutableList<Vec2>>()
    var boardId = 0
    var y = 0
    boards.put(boardId, mutableListOf())
    inputData.forEach {
        if (it.isEmpty()) {
            y = 0
            boardId += 1
            boards.put(boardId, mutableListOf())
        } else {
            for (x in it.indices) {
                if (it[x] == '#')
                    boards[boardId]!!.add(Vec2(x, y))
            }
            y += 1
        }
    }

    return boards.map { findReflectionIndex(it, smudged) }.sum()
}

fun findReflectionIndex(board: Map.Entry<Int, MutableList<Vec2>>, smudged: Boolean): Int {
    val maxX = board.value.maxOf { it.x }
    val maxY = board.value.maxOf { it.y }
    for (x in 0..<maxX) {
        var size = Math.min(x + 1, maxX - x)
        var rangeL = (x - size) + 1..x
        var rangeR = x + 1..x + size

        var errors = 0
        board.value.filter { (rangeL.first..rangeR.last).contains(it.x) }.forEach {
            if (board.value.filter { point ->
                    point == Vec2(
                        if (it.x <= x) {
                            (x - it.x) + x + 1
                        } else {
                            x - (it.x - x - 1)
                        }, it.y
                    )
                }.isEmpty()) {
                errors += 1
            }
        }
        if (smudged && errors == 1 || !smudged && errors == 0)
            return x + 1
    }
    for (y in 0..<maxY) {
        var size = Math.min(y + 1, maxY - y)
        var rangeT = (y - size) + 1..y
        var rangeB = y + 1..y + size

        var errors = 0
        board.value.filter { (rangeT.first..rangeB.last).contains(it.y) }.forEach {
            if (board.value.filter { point ->
                    point == Vec2(
                        it.x, if (it.y <= y) {
                            (y - it.y) + y + 1
                        } else {
                            y - (it.y - y - 1)
                        }
                    )
                }.isEmpty()) {
                errors += 1
            }
        }
        if (smudged && errors == 1 || !smudged && errors == 0)
            return (y + 1) * 100
    }
    return 0
}





