package day1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class day1Test {

    @Test
    fun part1() = run {
        val inputData = ("1abc2\n" +
                "pqr3stu8vwx\n" +
                "a1b2c3d4e5f\n" +
                "treb7uchet").split("\n")
        assertEquals(142, part1(inputData))
    }

    @Test
    fun part2() = run {
        val inputData = ("two1nine\n" +
                "eightwothree\n" +
                "abcone2threexyz\n" +
                "xtwone3four\n" +
                "4nineeightseven2\n" +
                "zoneight234\n" +
                "7pqrstsixteen").split("\n")
        assertEquals(281, part2(inputData))
    }

    @Test
    fun convertTextNumbersToInts() = assertEquals("x21e34r", replaceStringNumber("xtwone3four"))
}