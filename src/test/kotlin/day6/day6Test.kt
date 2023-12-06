package day6

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class day6Test {
    private lateinit var inputData: List<String>

    @BeforeEach
    fun setupTestData() {
        inputData = ("Time:      7  15   30\n" +
                "Distance:  9  40  200").split("\n")
    }

    @Test
    fun part1() = assertEquals(288, part1(inputData))

    @Test
    fun part2() = assertEquals(71503, part2(inputData))
}