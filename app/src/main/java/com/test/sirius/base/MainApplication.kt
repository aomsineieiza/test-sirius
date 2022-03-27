package com.test.sirius.base

import android.app.Application
import com.test.sirius.di.repositoryModule
import com.test.sirius.di.serviceModule
import com.test.sirius.di.useCaseModule
import com.test.sirius.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        setUpKoin()
    }

    private fun setUpKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            loadKoinModules(listOf(
                useCaseModule,
                viewModelModule,
                serviceModule,
                repositoryModule,
            ))
        }
    }

    companion object {
        lateinit var instance: MainApplication private set
    }
}