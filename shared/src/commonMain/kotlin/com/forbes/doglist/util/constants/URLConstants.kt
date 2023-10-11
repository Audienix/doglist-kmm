package com.forbes.doglist.util.constants

/**
 * Object containing constant URL values for the application.
 * It defines the base URL and various endpoints.
 *
 * @author Arighna Maity
 */
object URLConstants {

    const val BASE_URL = "https://dog.ceo/"

    const val DOG_LIST_ENDPOINT = "api/breeds/list/all"
    const val DOG_RANDOM_IMAGE_ENDPOINT = "api/breed/%s/images/random"
    const val DOG_IMAGE_LIST_ENDPOINT = "api/breed/%s/images/random/5"
}