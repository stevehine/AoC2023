package day12

import java.io.File
import java.util.*
import kotlin.time.measureTime

fun main() {
    val inputData = File("out/production/SteveH/day12Input").readLines()

    println(measureTime { println("Part 1: ${part1(inputData)}") }.toString())
    println(measureTime { println("Part 2: ${part2(inputData)}") }.toString())
}

val springRegex = Regex("#+")

fun part1(inputData: List<String>): Long {

    return inputData.map {
        val prefix = it.split(' ')[0]
        val suffix = it.split(' ')[1]
        countConfigurationsv2(prefix, suffix)
    }.sum()

}

fun part2(inputData: List<String>): Long {
    return inputData.map {
        val prefix = it.split(' ')[0]
        val suffix = it.split(' ')[1]
        countConfigurationsv2("$prefix?$prefix?$prefix?$prefix?$prefix", "$suffix,$suffix,$suffix,$suffix,$suffix")
    }.sum()
}

private fun countConfigurationsv2(prefix: String, suffix: String): Long {
    var cache = mutableMapOf<String, Long>()
    val groups = suffix.split(',').map { it.toInt() }

    return walkTheDots(cache, prefix, groups)
}

private fun walkTheDots(
    cache: MutableMap<String, Long>,
    prefix: String,
    groups: List<Int>,
    index: Int = 0,
    blockIndex: Int = 0,
    blockLength: Int = 0
): Long {
    var cacheKey = "$index,$blockIndex,$blockLength"
    if (cache.containsKey(cacheKey))
        return cache[cacheKey]!!

    if (index == prefix.length) {
        if (blockIndex == groups.size && blockLength == 0)
            return 1
        if (blockIndex == groups.size - 1 && groups[blockIndex] == blockLength)
            return 1
        return 0
    }
    var total = 0L

    listOf('.', '#').forEach {
        if (prefix[index] == it || prefix[index] == '?') {
            if (it == '.' && blockLength == 0) {
                total += walkTheDots(cache, prefix, groups, index + 1, blockIndex, 0)
            } else if (it == '.' && blockLength > 0 && blockIndex < groups.size && groups[blockIndex] == blockLength) {
                total += walkTheDots(cache, prefix, groups, index + 1, blockIndex + 1, 0)
            } else if (it == '#') {
                total += walkTheDots(cache,prefix,groups,index+1,blockIndex,blockLength+1)
            }
        }
    }

    cache.put(cacheKey, total)
    return total
}

private fun countConfigurationsv1(prefix: String, suffix: String, springRegex: Regex): Int {
    val configurationStack = Stack<String>()
    println("$prefix $suffix")
    configurationStack.add(prefix)
    val springCount = suffix.split(',').toList().sumOf { it.toInt() }
    val gapCount = suffix.split(',').count() + 2
    var totalMatches = 0
    while (
        configurationStack.isNotEmpty()
    ) {
        val configuration = configurationStack.pop()!!
        val configSpringCount = configuration.count { it == '#' }
        var configGapCount = 0;
        Regex("""\.+""").findAll(configuration).forEach { configGapCount += 1 }
        if (configSpringCount <= springCount && configGapCount <= gapCount) {

            if (configuration.contains('?')) {
                val index = configuration.indexOf('?')

                configurationStack.add(
                    configuration.replaceRange(index..index, ".")
                )

                if (configSpringCount < springCount) {
                    configurationStack.add(configuration.replaceRange(index..index, "#"))
                }
            } else {

                var springConfig = ""
                val matches = springRegex.findAll(configuration.replace("?", "."))
                matches.forEach {
                    springConfig += ",${it.range.endInclusive + 1 - it.range.start}"
                }
                springConfig = springConfig.trimStart(',')

                if (springConfig == suffix) {
                    println("Found Match: $configuration $suffix")
                    totalMatches += 1
                }
            }
        }
    }

    return totalMatches
}





