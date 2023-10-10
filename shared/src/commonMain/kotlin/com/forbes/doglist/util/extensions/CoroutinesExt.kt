package com.forbes.doglist.util.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

/**
 * This is the asyncAll coroutines extension function used in [DogListLoader].
 *
 * @author: Arighna Maity
 */
fun <T, V> CoroutineScope.asyncAll(list: List<T>, block: suspend (T) -> V): List<Deferred<V>> {
    return list.map {
        async { block.invoke(it) } // Creates a coroutine and returns its future result as an implementation of Deferred.
    }
}