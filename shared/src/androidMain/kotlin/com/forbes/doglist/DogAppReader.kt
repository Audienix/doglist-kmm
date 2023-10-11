package com.forbes.doglist

import com.forbes.doglist.core.dataSource.remote.DogAppLoader
import com.forbes.doglist.core.dataSource.remote.DogAppReader

/**
 * Create a [DogAppReader] instance.
 *
 * @return A new [DogAppReader] instance.
 * @author Arighna Maity
 */
fun DogAppReader.Companion.create() = DogAppReader(
    DogAppLoader(
        androidHttpClient()
    )
)