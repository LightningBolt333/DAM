package com.example.woofwoof.core.repository

import com.example.woofwoof.core.model.DogImage
import com.example.woofwoof.core.network.DogApiService

class DogRepository(private val apiService: DogApiService) {
    
    private val cachedDogs = mutableListOf<DogImage>()

    suspend fun getDogs(breed: String? = null): Result<List<DogImage>> {
        return try {
            val response = if (breed.isNullOrBlank()) {
                apiService.getRandomDogs()
            } else {
                apiService.getDogsByBreed(breed.trim().lowercase())
            }
            if (response.status == "success") {
                val newDogs = response.images.map { DogImage(imageUrl = it) }
                cachedDogs.clear()
                cachedDogs.addAll(newDogs)
                Result.success(newDogs)
            } else {
                Result.failure(Exception("Failed to fetch dogs: status ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getDogById(id: String): Result<DogImage> {
        val dog = cachedDogs.find { it.id == id }
        return if (dog != null) {
            Result.success(dog)
        } else {
            Result.failure(Exception("Dog with id $id not found"))
        }
    }
}
