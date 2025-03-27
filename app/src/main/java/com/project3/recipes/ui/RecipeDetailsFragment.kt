package com.project3.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.project3.recipes.R
import com.project3.recipes.data.model.Meal
import com.project3.recipes.ui.adapter.RecipesAdapter

class RecipeDetailsFragment(): Fragment() {
    private val recipe = Meal(
        "1",
        "Sushi",
        "a picture of sushi idk",
        listOf("some fish idk"),
        "Just don't cook it or something"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_recipe_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeNameText: TextView = view.findViewById(R.id.recipe_details_name)
        val recipeThumbnail: ImageView = view.findViewById(R.id.recipe_details_thumbnail)
        val recipeIngredientsList: ListView = view.findViewById(R.id.recipe_details_ingredients_list)
        val recipeInstructions: TextView = view.findViewById(R.id.recipe_details_instructions)

        recipeNameText.text = recipe.name
        recipeThumbnail.load(recipe.thumbnail)

        val ingredientsAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            recipe.ingredients
        )

        recipeIngredientsList.adapter = ingredientsAdapter

        recipeInstructions.text = recipe.instructions
    }
}