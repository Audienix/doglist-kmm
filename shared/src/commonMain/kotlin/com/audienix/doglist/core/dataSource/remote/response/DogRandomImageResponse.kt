package com.audienix.doglist.core.dataSource.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DogRandomImageResponse(
    @SerialName("message")
    val message: String = "",
    @SerialName("status")
    val status: String = ""
)