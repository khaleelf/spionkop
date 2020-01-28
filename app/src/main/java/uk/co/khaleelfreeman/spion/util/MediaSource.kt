package uk.co.khaleelfreeman.spion.util

import uk.co.khaleelfreeman.service.retrofit.dto.Article

fun mediaSources(articles: Array<Article>): Set<String> {
    return articles.map { it.url }.map {
        val indexOfFirstSeparator = it.indexOf('.')
        val subString = it.substring(indexOfFirstSeparator + 1, it.length)
        val indexOfSecondSeparator = subString.indexOf('.')
        it.substring(indexOfFirstSeparator + 1..indexOfFirstSeparator + indexOfSecondSeparator)
    }.toSet()
}