package com.forbes.doglist.util.extensions

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class CoroutinesExtensionTest {
    @Test
    fun testAsyncAll() = runBlocking {
        // Sample list of items to process asynchronously
        val inputList = listOf(1, 2, 3, 4, 5)

        // Call the asyncAll function to process the list using coroutines
        val deferredResults: List<Deferred<Int>> = asyncAll(inputList) { item ->
            // Simulate some asynchronous processing (e.g., computation)
            delay(100)
            item * 2 // Return a processed result
        }

        // Wait for all the deferred results to complete
        val results: List<Int> = deferredResults.map { it.await() }

        // Expected results based on the processing logic
        val expectedResults = listOf(2, 4, 6, 8, 10)

        // Verify that the actual results match the expected results
        assertEquals(expectedResults, results)
    }

    @Test
    fun testAsyncAllEmptyList() = runBlocking {
        val inputList = emptyList<Int>()
        val deferredResults: List<Deferred<Int>> = asyncAll(inputList) { item ->
            // Processing logic (should not be called for an empty list)
            item
        }
        assertEquals(emptyList(), deferredResults.map { it.await() })
    }

    @Test
    fun testAsyncAllLargeList() = runBlocking {
        val inputList = (1..10000).toList()
        val deferredResults: List<Deferred<Int>> = asyncAll(inputList) { item ->
            // Processing logic (e.g., item * 2)
            item * 2
        }
        val expectedResults = (2..20000 step 2).toList()
        assertEquals(expectedResults, deferredResults.map { it.await() })
    }

    @Test
    fun testAsyncAllLongRunning() = runBlocking {
        val inputList = (1..5).toList()
        val deferredResults: List<Deferred<Int>> = asyncAll(inputList) { item ->
            // Simulate a long-running task
            delay(1000)
            item * 2
        }
        // Ensure the processing doesn't block the main thread
        assertEquals(5, deferredResults.size)
    }
}
