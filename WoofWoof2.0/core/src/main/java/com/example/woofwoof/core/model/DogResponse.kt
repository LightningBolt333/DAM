package com.example.woofwoof.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DogResponse(
    @SerialName("message") val images: List<String>,
    @SerialName("status") val status: String
)
