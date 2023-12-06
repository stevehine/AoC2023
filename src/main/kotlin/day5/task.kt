package day5

import java.io.File
import kotlin.time.measureTime

fun main() {
    val inputData = File("out/production/SteveH/day5Input").readLines()

    println(measureTime { println("Part 1: ${part1(inputData)}") }.toString())
    println(measureTime { println("Part 2: ${part2(inputData)}") }.toString())

}

fun part1(inputData: List<String>): Long {

    val chunked = splitMappingData(inputData)
    val map = createMapping(chunked)
    val seedNumbers = chunked[0][0].split(' ').filter { it != "seeds:" }.map { it.toLong() }
    return computeMinimumValue(map, seedNumbers.map { it..it })
}

fun createMapping(chunked: List<List<String>>): List<Mapping> {
    val map = mutableListOf<Mapping>()
    chunked.forEach { chunk ->
        if (chunk.size != 1) {
            val ranges = mutableListOf<MappingRange>()

            for (i in 1..<chunk.size) {
                val mapPieces = chunk[i].split(' ').map { it.toLong() }
                ranges.add(MappingRange(mapPieces[1]..<mapPieces[1] + mapPieces[2], mapPieces[0] - mapPieces[1]))
            }
            map.add(Mapping(ranges))
        }
    }
    return map
}

fun computeMinimumValue(map: List<Mapping>, seedNumbers: List<LongRange>): Long {

    var minValue: Long = Long.MAX_VALUE
    seedNumbers.forEach { range ->
        for (s in range) {
            var currentValue = s
            map.forEach {
                currentValue = it.mapValue(currentValue)
            }
            if (currentValue < minValue)
                minValue = currentValue
        }
    }
    return minValue
}


fun part2(inputData: List<String>): Long {
    val chunked = splitMappingData(inputData)
    val map = createMapping(chunked)
    val seedNumbers = chunked[0][0].split(' ')
            .filter { it != "seeds:" }
            .chunked(2)
            .map { seeds -> seeds.map { it.toLong() } }.map {
                it.first()..<it.first() + it.last()
            }
    return computeMinimumValue(map, seedNumbers)
}


fun splitMappingData(inputData: List<String>): List<List<String>> {

    var current = mutableListOf<String>()
    val result = mutableListOf<List<String>>()

    inputData.forEach {
        if (it == "") {
            result.add(current)
            current = mutableListOf()
        } else
            current.add(it)
    }
    result.add(current)
    return result
}

class Mapping(private val mappingRanges: List<MappingRange>) {
    fun mapValue(value: Long): Long {
        mappingRanges.forEach { range ->
            if (range.source.contains(value))
                return value + range.modifier
        }
        return value
    }
}

class MappingRange(val source: LongRange, val modifier: Long)