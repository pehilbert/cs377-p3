package com.project3.recipes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Main data class for recipes, both for favorites and recipe search screen
@Entity(tableName = "favorite_meals")
data class Meal(
    @PrimaryKey val id: String,
    val name: String,
    val thumbnail: String,
    val ingredients: String,
    val instructions: String
)