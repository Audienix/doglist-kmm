package com.forbes.doglist.core.dataSource.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DogListApiResponse(
    @SerialName("message")
    val message: Map<String, List<String>> = mapOf(),
    @SerialName("status")
    val status: String = ""
)