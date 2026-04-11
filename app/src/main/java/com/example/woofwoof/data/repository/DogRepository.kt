package com.example.woofwoof.data.repository

import com.example.woofwoof.data.api.DogApiService
import com.example.woofwoof.data.api.DogResponse

class DogRepository(private val apiService: DogApiService) {
    suspend fun getDogs(breed: String? = null): Result<List<String>> {
        return try {
            val response = if (breed.isNullOrBlank()) {
                apiService.getRandomDogs()
            } else {
                apiService.getDogsByBreed(breed.trim().lowercase())
            }
            if (response.status == "success") {
                Result.success(response.images)
            } else {
                Result.failure(Exception("Failed to fetch dogs: status ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
