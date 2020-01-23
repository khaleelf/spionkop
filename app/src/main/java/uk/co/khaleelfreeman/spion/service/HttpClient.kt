package uk.co.khaleelfreeman.spion.service

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.IOException
import java.lang.String


interface HttpClient {
    val service: Articles
}

class RetrofitClient : HttpClient {
    private val retrofit = RetrofitFactory.from(Environment.PRODUCTION)
    override val service: Articles = retrofit.create(Articles::class.java)
}

interface Articles {
    @GET("liverpoolfc/articles")
    fun getArticles(): Call<ArticleResponse>
}

enum class Environment {
    PRODUCTION,
    TEST
}

object RetrofitFactory {
    fun from(env: Environment): Retrofit {
        return when (env) {
            Environment.PRODUCTION -> Retrofit.Builder()
                .baseUrl("https://www.khaleelfreeman.co.uk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            Environment.TEST -> {
                val client = OkHttpClient.Builder()
                    .addInterceptor(LoggingInterceptor())
                    .build()

                    Retrofit.Builder()
                        .client(client)
                        .baseUrl("https://www.khaleelfreeman.co.uk/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }


        }
    }
}

class LoggingInterceptor : Interceptor {

    val logTag = javaClass.name

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val t1 = System.nanoTime()
        Log.i(logTag,
            String.format(
                "Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()
            )
        )
        val response: Response = chain.proceed(request)
        val t2 = System.nanoTime()
        Log.i(logTag,
            String.format(
                "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6, response.headers()
            )
        )
        return response
    }
}