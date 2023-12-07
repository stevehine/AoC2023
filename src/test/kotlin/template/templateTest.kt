package template

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class templateTest {
    private lateinit var inputData: List<String>

    @BeforeEach
    fun setupTestData() {
        inputData = ("").split("\n")

    }

    @Test
    fun part1() = assertEquals(-1, part1(inputData))

    @Test
    fun part2() = assertEquals(-1, part2(inputData))
}