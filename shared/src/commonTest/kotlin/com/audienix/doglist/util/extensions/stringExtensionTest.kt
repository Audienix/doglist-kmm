package com.audienix.doglist.util.extensions

import kotlin.test.Test
import kotlin.test.assertEquals

class StringExtensionTest {
    @Test
    fun testCapitalizeFirstLetter() {
        val input = "hello, world"
        val expected = "Hello, world"
        val result = input.capitalizeFirstLetter()
        assertEquals(expected, result)
    }
}