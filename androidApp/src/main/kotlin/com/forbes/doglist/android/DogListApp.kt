package com.forbes.doglist.android

import android.app.Application
import com.forbes.doglist.app.FeedStore
import com.forbes.doglist.core.dataSource.remote.DogFeedReader
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
        single { DogFeedReader.create() }
        single { FeedStore(get()) }
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@DogListApp)
            modules(appModule)
        }
    }
}
