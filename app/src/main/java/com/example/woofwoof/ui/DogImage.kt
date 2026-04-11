package com.example.woofwoof.ui

import java.util.UUID

data class DogImage(
    val id: String = UUID.randomUUID().toString(),
    val imageUrl: String
)
