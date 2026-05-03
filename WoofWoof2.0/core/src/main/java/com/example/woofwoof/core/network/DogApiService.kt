package com.example.woofwoof.core.network

import com.example.woofwoof.core.model.DogResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class DogApiService(private val client: HttpClient) {
    suspend fun getRandomDogs(): DogResponse {
        return client.get("breeds/image/random/20").body()
    }

    suspend fun getDogsByBreed(breed: String): DogResponse {
        return client.get("breed/$breed/images/random/20").body()
    }
}
