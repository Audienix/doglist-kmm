package com.forbes.doglist

import com.forbes.doglist.core.dataSource.remote.DogAppLoader
import com.forbes.doglist.core.dataSource.remote.DogAppReader

fun DogAppReader.Companion.create() = DogAppReader(
    DogAppLoader(
        androidHttpClient()
    )
)