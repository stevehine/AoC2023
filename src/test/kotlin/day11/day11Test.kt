package day11

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class day11Test {
    private lateinit var inputData: List<String>

    @BeforeEach
    fun setupTestData() {
        inputData = ("...#......\n" +
                ".......#..\n" +
                "#.........\n" +
                "..........\n" +
                "......#...\n" +
                ".#........\n" +
                ".........#\n" +
                "..........\n" +
                ".......#..\n" +
                "#...#.....").split("\n")

    }

    @Test
    fun part1() = assertEquals(374.toBigInteger(), part1(inputData))

    @Test
    fun part2scale10() = assertEquals(1030.toBigInteger(), part2(inputData, 9))

    @Test
    fun part2scale100() = assertEquals(8410.toBigInteger(), part2(inputData, 99))
}