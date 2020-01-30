package uk.co.khaleelfreeman.service.retrofit.dto

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("feed_last_published")
    val published: Long,
    val articles: Array<Article>
)