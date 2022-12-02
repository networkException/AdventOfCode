package de.nwex.adventofcode

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val elfList = Path("input/day01").readLines().split(String::isEmpty).map { it.map(String::toInt).sum() }

    println("The elf carrying the most calories is carrying ${elfList.max()} calories.")
    println("The top three elves carrying the most calories are carrying ${elfList.sorted().takeLast(3).sum()} calories.")
}

fun <E> Collection<E>.split(predicate: (E) -> Boolean): List<List<E>> {
    val result = mutableListOf<MutableList<E>>(mutableListOf())
    for (element in this)
        if (predicate(element)) result.add(mutableListOf()) else result.last().add(element)
    return result
}
