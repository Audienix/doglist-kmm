package com.forbes.doglist

import com.forbes.doglist.core.dataSource.remote.DogFeedLoader
import com.forbes.doglist.core.dataSource.remote.DogFeedReader

fun DogFeedReader.Companion.create() = DogFeedReader(
    DogFeedLoader(
        androidHttpClient()
    )
)