package com.forbes.doglist.core.dataSource.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DogImageListResponse(
    @SerialName("message")
    val message: List<String> = listOf(),
    @SerialName("status")
    val status: String = ""
)