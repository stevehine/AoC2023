package day9

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class day9Test {
    private lateinit var inputData: List<String>

    @BeforeEach
    fun setupTestData() {
        inputData = ("0 3 6 9 12 15\n" +
                "1 3 6 10 15 21\n" +
                "10 13 16 21 30 45").split("\n")

    }

    @Test
    fun part1() = assertEquals(114, part1(inputData))

    @Test
    fun part2() = assertEquals(2, part2(inputData))
}