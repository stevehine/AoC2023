package day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class day10Test {

    @ParameterizedTest
    @ValueSource(
        strings = ["4,-L|F7\n7S-7|\nL|7||\n-L-J|\nL|-JF",
            "8,..F7.\n.FJ|.\nSJ.L7\n|F--J\nLJ..."]
    )
    fun part1(inputData: String) =
        assertEquals(inputData.split(',')[0].toInt(), part1(inputData.split(',')[1].split("\n")))

    @ParameterizedTest
    @ValueSource(
        strings = ["4,...........\n.S-------7.\n.|F-----7|.\n.||.....||.\n.||.....||.\n.|L-7.F-J|.\n.|..|.|..|.\n.L--J.L--J.\n..........."]
    )
    fun part2(inputData: String) =
        assertEquals(inputData.split(',')[0].toInt(), part2(inputData.split(',')[1].split("\n")))
}