package com.audienix.doglist.util.extensions

/**
 * Extension function to capitalize the first letter of a string.
 *
 * @receiver The string to be modified.
 * @return The string with the first letter capitalized.
 * @author Arighna Maity
 */
fun String.capitalizeFirstLetter(): String {
    return replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
}
