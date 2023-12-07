package day7

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class day7Test {
    private lateinit var inputData: List<String>

    @BeforeEach
    fun setupTestData() {
        inputData = ("32T3K 765\n" +
                "T55J5 684\n" +
                "KK677 28\n" +
                "KTJJT 220\n" +
                "QQQJA 483").split("\n")
    }

    @Test
    fun part1() = assertEquals(6440, part1(inputData))

    @Test
    fun part2() = assertEquals(5905, part2(inputData))
}