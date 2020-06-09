package uk.co.khaleelfreeman.spion.di

import android.app.Application
import okhttp3.OkHttpClient
import okhttp3.mock.*
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import uk.co.khaleelfreeman.service.HttpClient
import uk.co.khaleelfreeman.service.retrofit.Articles
import uk.co.khaleelfreeman.service.retrofit.RetrofitFactory

class SpionKopApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { modules(module) }
    }
}