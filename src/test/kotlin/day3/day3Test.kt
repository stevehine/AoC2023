package day3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class day3Test {
    private lateinit var inputData: List<String>
    @BeforeEach
        fun setupTestData() {
            inputData = ("467..114..\n" +
                    "...*......\n" +
                    "..35..633.\n" +
                    "......#...\n" +
                    "617*......\n" +
                    ".....+.58.\n" +
                    "..592.....\n" +
                    "......755.\n" +
                    "...\$.*....\n" +
                    ".664.598..").split("\n")
        }
    @Test
    fun part1() = assertEquals(4361, part1(inputData))

    @Test
    fun part2() = assertEquals(467835, part2(inputData))
}