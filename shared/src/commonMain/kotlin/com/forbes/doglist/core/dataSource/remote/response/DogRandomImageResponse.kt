package com.forbes.doglist.core.dataSource.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DogRandomImageApiResponse(
    @SerialName("message")
    val message: String = "",
    @SerialName("status")
    val status: String = ""
)