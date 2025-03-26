package com.project3.recipes.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface MealApiService {
    @GET("/search.php?s={searchTerm}")
    suspend fun getMealsBySearchTerm(@Path("searchTerm") searchTerm: String): MealResponse
}