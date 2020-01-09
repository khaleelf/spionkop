package uk.co.khaleelfreeman.spion.util

import uk.co.khaleelfreeman.spion.service.Article
import java.util.*

fun formatDate(article: Article): String {
    val dateWords =
        Date(article.timeStamp.toLong()).toString().split(" ", ignoreCase = true)
    return "${dateWords[0]} ${dateWords[1]} ${dateWords[2]}"
}