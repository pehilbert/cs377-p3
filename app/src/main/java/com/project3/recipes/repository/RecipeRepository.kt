package com.project3.recipes.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.project3.recipes.data.api.MealResponseItem
import com.project3.recipes.data.api.RetrofitClient
import com.project3.recipes.data.model.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository {
    private val meals = mutableListOf<Meal>()
    private val mealsLiveData = MutableLiveData<List<Meal>>()

    suspend fun fetchMealsWithSearchTerm(searchTerm: String): List<Meal> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getMealsBySearchTerm(searchTerm)

                val meals = response.meals.map { item ->
                    mealResponseItemToMeal(item)
                }

                meals
            } catch (e: Exception) {
                Log.e("RecipeRepository", "Error fetching meals", e)
                emptyList()
            }
        }
    }

    private fun mealResponseItemToMeal(mealResponseItem: MealResponseItem) : Meal {
        return Meal("1", "Example", "idk", listOf("I hate this class"), listOf("End me"))
    }
}