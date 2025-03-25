package com.project3.recipes.data

data class Recipe(val name: String,
                  val thumbnail: String,
                  val ingredients: List<String>,
                  val instructions: List<String>
)