package uk.co.khaleelfreeman.spion.util

import java.util.*

/**
 * Playing around with function composition.
 */

fun formatTimeStamp(timeStamp: Long): String {
    return compose(::keyDateInfo, ::dateInformation)(Date(timeStamp))
}

private fun keyDateInfo(dateInfo: Iterable<String>): String {
    return dateInfo.take(3).reduce { acc, item -> "$acc $item" }
}

private fun dateInformation(date: Date): Iterable<String> {
    return date.toString().split(" ", ignoreCase = true)
}

fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
    return { x -> f(g(x)) }
}