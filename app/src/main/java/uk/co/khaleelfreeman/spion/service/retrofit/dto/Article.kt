package uk.co.khaleelfreeman.spion.service.retrofit.dto

import co.uk.khaleelfreeman.service.retrofit.dto.Visual
import com.google.gson.annotations.SerializedName

data class Article(
    val title: String = "",
    val visual: Visual = Visual(),
    @SerializedName("published")
    val timeStamp: Long = 0L,
    @SerializedName("originId")
    val url: String = ""
)