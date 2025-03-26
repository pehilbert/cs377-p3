package com.project3.recipes.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MealApiService {
    @GET("search.php")
    suspend fun getMealsBySearchTerm(@Query("s") searchTerm: String): MealResponse
}