package uk.co.khaleelfreeman.spion.service.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    fun builder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://www.khaleelfreeman.co.uk/")
            .addConverterFactory(GsonConverterFactory.create())
    }
}