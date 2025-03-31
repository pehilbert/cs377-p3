package com.project3.recipes.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.project3.recipes.data.model.Meal
import kotlinx.coroutines.flow.Flow
import androidx.room.Query

@Dao
interface MealDao {
    @Insert
    suspend fun addFavoriteMeal(meal: Meal)

    @Query("SELECT * FROM favorite_meals")
    fun getFavoriteMeals(): Flow<List<Meal>>

    @Delete
    suspend fun removeFavoriteMeal(meal: Meal)
}