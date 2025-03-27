package com.project3.recipes.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.project3.recipes.R
import com.project3.recipes.data.model.Meal
import com.project3.recipes.repository.RecipeRepository
import com.project3.recipes.ui.adapter.RecipesAdapter
import com.project3.recipes.viewmodel.RecipeViewModel
import com.project3.recipes.viewmodel.RecipeViewModelFactory

class RecipeDetailsFragment(): Fragment() {
    private val recipeViewModel: RecipeViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            RecipeViewModelFactory(RecipeRepository())
        ).get(RecipeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_recipe_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get UI elements
        val recipeNameText: TextView = view.findViewById(R.id.recipe_details_name)
        val recipeThumbnail: ImageView = view.findViewById(R.id.recipe_details_thumbnail)
        val recipeIngredientsList: TextView = view.findViewById(R.id.recipe_details_ingredients_list)
        val recipeInstructions: TextView = view.findViewById(R.id.recipe_details_instructions)

        // Populate UI elements with proper values
        val recipe = recipeViewModel.currentRecipeDetails.value
        Log.d("RecipeDetailsFragment", "Displaying recipe details: $recipe")

        recipeNameText.text = recipe?.name
        recipeThumbnail.load(recipe?.thumbnail)

        if (recipe != null) {
            // Put the list of ingredients into a single string, which is a bulleted list
            val ingredientsText = recipe?.ingredients?.joinToString("\n") { "• $it" }

            // Set text of ingredients list
            recipeIngredientsList.text = ingredientsText
        }

        recipeInstructions.text = recipe?.instructions
    }
}