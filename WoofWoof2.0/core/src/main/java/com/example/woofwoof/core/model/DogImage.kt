package com.example.woofwoof.core.model

import java.util.UUID
import kotlinx.serialization.Serializable

@Serializable
data class DogImage(
    val id: String = UUID.randomUUID().toString(),
    val imageUrl: String
)
