package uk.co.khaleelfreeman.spionkoparticledomain.util

import uk.co.khaleelfreeman.spionkoparticledomain.SpionkopArticle

fun mediaSources(articles: List<SpionkopArticle>): Set<String> {
    return articles.map { it.url }.map {
        val indexOfFirstSeparator = it.indexOf('.')
        val subString = it.substring(indexOfFirstSeparator + 1, it.length)
        val indexOfSecondSeparator = subString.indexOf('.')
        it.substring(indexOfFirstSeparator + 1..indexOfFirstSeparator + indexOfSecondSeparator)
    }.toSet()
}