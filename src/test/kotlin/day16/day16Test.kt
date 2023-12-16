package day16

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class day16Test {
    private lateinit var inputData: List<String>

    @BeforeEach
    fun setupTestData() {
        inputData = (".|...\\....\n" +
                "|.-.\\.....\n" +
                ".....|-...\n" +
                "........|.\n" +
                "..........\n" +
                ".........\\\n" +
                "..../.\\\\..\n" +
                ".-.-/..|..\n" +
                ".|....-|.\\\n" +
                "..//.|....").split("\n")

    }

    @Test
    fun part1() = assertEquals(46, part1(inputData))

    @Test
    fun part2() = assertEquals(51, part2(inputData))
}