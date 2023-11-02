package com.audienix.doglist

import com.audienix.doglist.util.constants.NumberConstants.CONNECT_TIMEOUT
import com.audienix.doglist.util.constants.NumberConstants.REQUEST_TIMEOUT
import com.audienix.doglist.util.constants.NumberConstants.SOCKET_TIMEOUT
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import java.util.concurrent.TimeUnit

/**
 * Create an HTTP client for Android using OkHttp.
 *
 * @return An instance of HttpClient configured for Android.
 * @author Arighna Maity
 */
internal fun androidHttpClient() = HttpClient(OkHttp) {
    engine {
        config {
            retryOnConnectionFailure(true)
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        }
    }
    install(ContentNegotiation) {
        json()
    }
    install(HttpTimeout) {
        requestTimeoutMillis = REQUEST_TIMEOUT
        connectTimeoutMillis = CONNECT_TIMEOUT
        socketTimeoutMillis = SOCKET_TIMEOUT
    }
}
