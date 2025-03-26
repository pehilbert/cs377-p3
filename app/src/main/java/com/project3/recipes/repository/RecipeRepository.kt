package com.project3.recipes.repository

import android.util.Log
import com.project3.recipes.data.network.MealResponseItem
import com.project3.recipes.data.network.RetrofitClient
import com.project3.recipes.data.model.Meal
import com.project3.recipes.data.network.MealResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository {
    suspend fun fetchMealsWithSearchTerm(searchTerm: String): MealResponse {
        val response = RetrofitClient.apiService.getMealsBySearchTerm(searchTerm)
        Log.d("RecipeRepository", "MealResponse: ${response.meals.map {it.strMeal}}")
        return response
    }
}