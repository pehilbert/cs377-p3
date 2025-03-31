package com.project3.recipes.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.project3.recipes.data.model.Meal
import com.project3.recipes.data.network.MealResponse
import com.project3.recipes.data.network.MealResponseItem
import com.project3.recipes.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
    private val _fetchedRecipes = MutableLiveData<List<Meal>>()
    private val _currentRecipeDetails = MutableLiveData<Meal>()
    private val _isLoading = MutableLiveData(false)
    private val _apiError = MutableLiveData(false)

    val isLoading: LiveData<Boolean> get() = _isLoading
    val apiError: LiveData<Boolean> get() = _apiError
    val fetchedRecipes: LiveData<List<Meal>> get() = _fetchedRecipes
    val currentRecipeDetails: LiveData<Meal> get() = _currentRecipeDetails
    val favoriteRecipes: LiveData<List<Meal>> = repository.getFavoriteMeals().asLiveData()

    // Dynamically fetch recipes from the MealDB API
    fun searchForRecipes(searchTerm: String) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                // convert each MealResponseItem to a Meal before updating fetched recipes
                val response: MealResponse = repository.fetchMealsWithSearchTerm(searchTerm)
                _fetchedRecipes.value = response.meals
                    ?.map { item -> mealResponseItemToMeal(item) }
                    ?: emptyList()

                _apiError.value = false
                Log.d("RecipeViewModel", "Updated fetched recipes: ${_fetchedRecipes.value!!.map { it.name }}")
            } catch (exception: Exception) {
                _apiError.value = true
                _fetchedRecipes.value = emptyList()
                Log.e("RecipeViewModel", "Error searching for recipes: ${exception.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Update the recipe details screen
    fun setRecipeDetails(recipe: Meal) {
        Log.d("RecipeViewModel", "Set recipe details to ${recipe.name}")
        _currentRecipeDetails.value = recipe
    }

    // Add a recipe to favorites
    fun addToFavorites(recipe: Meal) {
        Log.d("RecipeViewModel", "Adding to favorites: ${recipe.name}")

        viewModelScope.launch {
            repository.addFavoriteMeal(recipe)
        }
    }

    // Remove a recipe from favorites
    fun removeFromFavorites(recipe: Meal) {
        Log.d("RecipeViewModel", "Removing from favorites: ${recipe.name}")

        viewModelScope.launch {
            repository.removeFavoriteMeal(recipe)
        }
    }

    // Utility to map a MealResponseItem object to a more usable Meal object
    private fun mealResponseItemToMeal(item: MealResponseItem): Meal {
        // Convert the ingredients and measures to a single list of strings
        val ingredients = mutableListOf<String>()
        for (i in 1..20) {
            val ingredient = item.getIngredient(i)
            val measure = item.getMeasure(i)
            if (!ingredient.isNullOrBlank()) {
                ingredients.add("${measure.orEmpty()} ${ingredient.trim()}".trim())
            }
        }

        val strIngredients = ingredients.joinToString("\n") { "• $it" }

        // populate Meal object with the rest of the values
        return Meal(
            id = item.idMeal,
            name = item.strMeal,
            thumbnail = item.strMealThumb,
            ingredients = strIngredients,
            instructions = item.strInstructions
        )
    }

    // These functions were written with the assistance of ChatGPT
    private fun MealResponseItem.getIngredient(index: Int): String? {
        return this::class.java.getDeclaredField("strIngredient$index").apply { isAccessible = true }.get(this) as? String
    }

    private fun MealResponseItem.getMeasure(index: Int): String? {
        return this::class.java.getDeclaredField("strMeasure$index").apply { isAccessible = true }.get(this) as? String
    }
}