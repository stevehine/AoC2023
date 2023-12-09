package day8

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class day8Test {
    private lateinit var inputData: List<String>

    @Test
    fun part1() {
        inputData = ("LLR\n" +
                "\n" +
                "AAA = (BBB, BBB)\n" +
                "BBB = (AAA, ZZZ)\n" +
                "ZZZ = (ZZZ, ZZZ)").split("\n")

        assertEquals(6, part1(inputData))
    }

    @Test
    fun part2() {
        inputData = ("LR\n" +
                "\n" +
                "11A = (11B, XXX)\n" +
                "11B = (XXX, 11Z)\n" +
                "11Z = (11B, XXX)\n" +
                "22A = (22B, XXX)\n" +
                "22B = (22C, 22C)\n" +
                "22C = (22Z, 22Z)\n" +
                "22Z = (22B, 22B)\n" +
                "XXX = (XXX, XXX)").split("\n")
        assertEquals(6, part2(inputData))
    }
}