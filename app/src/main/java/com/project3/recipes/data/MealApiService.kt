package com.project3.recipes.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface MealApiService {
    @GET("/search.php?s={searchTerm}")
    suspend fun getMealsBySearchTerm(@Path("searchTerm") searchTerm: String): List<Recipe>
}

object RetrofitClient {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1"
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: MealApiService by lazy {
        retrofit.create(MealApiService::class.java)
    }
}