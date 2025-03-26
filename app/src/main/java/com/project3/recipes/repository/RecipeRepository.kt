package com.project3.recipes.repository

import android.util.Log
import com.project3.recipes.data.network.MealResponseItem
import com.project3.recipes.data.network.RetrofitClient
import com.project3.recipes.data.model.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository {
    suspend fun fetchMealsWithSearchTerm(searchTerm: String): List<Meal> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getMealsBySearchTerm(searchTerm)

                val updatedMeals = response.meals.map { item ->
                    mealResponseItemToMeal(item)
                }

                updatedMeals
            } catch (e: Exception) {
                Log.e("RecipeRepository", "Error fetching meals", e)
                emptyList()
            }
        }
    }

    private fun mealResponseItemToMeal(mealResponseItem: MealResponseItem) : Meal {
        return Meal("1", "Example", "idk", listOf("1 cup I hate this class"), listOf("1. End me"))
    }
}