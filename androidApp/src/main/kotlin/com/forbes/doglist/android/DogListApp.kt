package com.forbes.doglist.android

import android.app.Application
import com.forbes.doglist.app.DogDetailsStore
import com.forbes.doglist.app.DogListStore
import com.forbes.doglist.core.dataSource.remote.DogAppReader
import com.forbes.doglist.create
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

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
