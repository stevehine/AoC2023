package day13

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class day13Test {
    private lateinit var inputData: List<String>

    @BeforeEach
    fun setupTestData() {
        inputData = ("#.##..##.\n" +
                "..#.##.#.\n" +
                "##......#\n" +
                "##......#\n" +
                "..#.##.#.\n" +
                "..##..##.\n" +
                "#.#.##.#.\n" +
                "\n" +
                "#...##..#\n" +
                "#....#..#\n" +
                "..##..###\n" +
                "#####.##.\n" +
                "#####.##.\n" +
                "..##..###\n" +
                "#....#..#").split("\n")

    }

    @Test
    fun part1() = assertEquals(405, part1(inputData))

    @Test
    fun part2() = assertEquals(400, part2(inputData))
}