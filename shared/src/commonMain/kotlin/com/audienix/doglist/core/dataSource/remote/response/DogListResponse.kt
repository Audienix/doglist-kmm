package com.audienix.doglist.core.dataSource.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DogListResponse(
    @SerialName("message")
    val message: Map<String, List<String>> = mapOf(),
    @SerialName("status")
    val status: String = ""
)