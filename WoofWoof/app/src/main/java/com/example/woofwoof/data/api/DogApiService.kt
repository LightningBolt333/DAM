package com.example.woofwoof.data.api

import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("breeds/image/random/20")
    suspend fun getRandomDogs(): DogResponse

    @GET("breed/{breed}/images/random/20")
    suspend fun getDogsByBreed(@Path("breed") breed: String): DogResponse
}
