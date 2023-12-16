package day15

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class day15Test {
    private lateinit var inputData: List<String>

    @BeforeEach
    fun setupTestData() {
        inputData = ("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7").split("\n")

    }

    @Test
    fun part1() = assertEquals(1320, part1(inputData))

    @Test
    fun part2() = assertEquals(145, part2(inputData))
}