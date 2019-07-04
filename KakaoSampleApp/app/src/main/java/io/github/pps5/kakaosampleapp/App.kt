package io.github.pps5.kakaosampleapp

import android.app.Application
import io.github.pps5.kakaosampleapp.di.httpModule
import io.github.pps5.kakaosampleapp.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(
                httpModule, repositoryModule
            ))
        }
    }
}