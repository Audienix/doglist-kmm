package com.forbes.doglist.core.dataSource.remote

import com.forbes.doglist.core.models.DogBreed
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class DogFeedReader internal constructor(
    private val feedLoader: DogFeedLoader
) {
    @Throws(Exception::class)
    suspend fun getDogList(
        forceUpdate: Boolean = false
    ): ApiResult<List<DogBreed>> {

        return feedLoader.getDogFeed()
    }

    private suspend fun <A, B> Iterable<A>.mapAsync(f: suspend (A) -> B): List<B> =
        coroutineScope { map { async { f(it) } }.awaitAll() }

    companion object
}
