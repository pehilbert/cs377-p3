package com.project3.recipes.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    // Search for recipes from the MealDB API
    @GET("search.php")
    suspend fun getMealsBySearchTerm(@Query("s") searchTerm: String): MealResponse
}