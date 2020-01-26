package uk.co.khaleelfreeman.spion.di

import android.app.Application
import org.koin.core.context.startKoin

class SpionKopApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { modules(module) }
    }
}