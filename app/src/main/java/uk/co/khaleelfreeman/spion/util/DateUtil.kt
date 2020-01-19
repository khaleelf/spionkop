package uk.co.khaleelfreeman.spion.util

import java.util.*

// TODO: Can this final method call be made nicer.

fun formatTimeStamp(timeStamp: Long): String {
    return keyDateInfo(
        dateInformation(
            Date(timeStamp)
        )
    )
}

private fun keyDateInfo(dateInfo: Iterable<String>): String {
    return dateInfo.take(3).reduce { acc, item -> "$acc $item" }
}

private fun dateInformation(date: Date): Iterable<String> {
    return date.toString().split(" ", ignoreCase = true)
}