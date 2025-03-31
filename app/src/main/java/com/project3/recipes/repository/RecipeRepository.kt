package com.project3.recipes.repository

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.project3.recipes.data.database.MealDao
import com.project3.recipes.data.network.MealResponseItem
import com.project3.recipes.data.network.RetrofitClient
import com.project3.recipes.data.model.Meal
import com.project3.recipes.data.network.MealResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RecipeRepository(private val mealDao: MealDao) {
    // Search for meals with the MealDB API
    suspend fun fetchMealsWithSearchTerm(searchTerm: String): MealResponse {
        val response = RetrofitClient.apiService.getMealsBySearchTerm(searchTerm)
        Log.d("RecipeRepository", "MealResponse: ${response.meals?.map {it.strMeal} ?: emptyList()}")
        return response
    }

    // Add a favorite meal to the database
    suspend fun addFavoriteMeal(meal: Meal) {
        try {
            mealDao.addFavoriteMeal(meal)
        } catch (e: SQLiteConstraintException) {
            Log.d("RecipeViewModel", "${meal.name} already in favorites")
        }
    }

    // Get list of favorite meals from the database
    fun getFavoriteMeals(): Flow<List<Meal>> {
        return mealDao.getFavoriteMeals()
    }

    // Remove a meal from favorites
    suspend fun removeFavoriteMeal(meal: Meal) {
        mealDao.removeFavoriteMeal(meal)
    }
}