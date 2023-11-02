package com.audienix.doglist

import com.audienix.doglist.core.dataSource.remote.DogAppLoader
import com.audienix.doglist.core.dataSource.remote.DogAppReader

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