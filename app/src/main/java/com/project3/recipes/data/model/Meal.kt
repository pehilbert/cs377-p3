package com.project3.recipes.data.model

data class Meal(
    val id: String,
    val name: String,
    val thumbnail: String,
    val ingredients: List<String>,
    val instructions: List<String>
)