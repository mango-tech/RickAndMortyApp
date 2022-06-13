package com.mango.android.rickmortyapp

import android.app.Application
import com.mango.android.rickmortyapp.di.appModules
import es.andres.bailen.data.repository.di.dataModules
import es.andres.bailen.domain.di.domainModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class RickMortyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(applicationContext)
            modules(appModules + domainModules + dataModules)
        }
    }
}