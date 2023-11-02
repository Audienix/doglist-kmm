package com.audienix.doglist.android

import android.app.Application
import com.audienix.doglist.app.DogDetailsStore
import com.audienix.doglist.app.DogListStore
import com.audienix.doglist.core.dataSource.remote.DogAppReader
import com.audienix.doglist.create
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Application class for the Dog List app.
 *
 * @author Arighna Maity
 */
class DogListApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private val appModule = module {
        single { DogAppReader.create() }
        single { DogListStore(get()) }
        single { DogDetailsStore(get()) }
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@DogListApp)
            modules(appModule)
        }
    }
}
