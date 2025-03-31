package com.project3.recipes.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.project3.recipes.R
import com.project3.recipes.data.model.Meal
import com.project3.recipes.repository.RecipeRepository
import com.project3.recipes.viewmodel.RecipeViewModel
import com.project3.recipes.viewmodel.RecipeViewModelFactory

class RecipesAdapter(
    private var meals: List<Meal>,
    private val recipeViewModel: RecipeViewModel
) : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {
    init {
        Log.d("RecipesAdapter", "meals: ${meals.map {it.name}}")
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMeals(newMeals: List<Meal>) {
        meals = newMeals
        notifyDataSetChanged()
    }

    // Get UI elements in each list item
    class RecipesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeName: TextView = view.findViewById(R.id.recipe_name)
        val thumbnail: ImageView = view.findViewById(R.id.recipe_thumbnail)
        val detailsButton: Button = view.findViewById(R.id.details_button)
        val favoriteButton: ImageButton = view.findViewById(R.id.favorite_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return RecipesViewHolder(view)
    }

    // Populate each list item with a particular recipe item
    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val meal = meals[position]
        holder.recipeName.text = meal.name
        holder.thumbnail.load(meal.thumbnail) {
            placeholder(R.drawable.thumbnail_placeholder)
            error(R.drawable.thumbnail_error)
        }

        // Set current recipe details in ViewModel, and navigate to recipe details screen
        // when the details button is clicked
        holder.detailsButton.setOnClickListener {
            recipeViewModel.setRecipeDetails(meal)
            holder.itemView.findNavController().navigate(R.id.nav_recipe_details)
        }

        // Manage favorite functionality depending on whether or not this recipe is in favorites
        val inFavorites = recipeViewModel.favoriteRecipes.value?.any { it.id == meal.id } == true

        // Set star button filled or not depending on whether or not it is favorites screen
        if (inFavorites) {
            holder.favoriteButton.setImageResource(R.drawable.baseline_star_24)
        } else {
            holder.favoriteButton.setImageResource(R.drawable.baseline_star_border_24)
        }

        // Listen for click on add to favorites button, add/remove to favorites through ViewModel
        holder.favoriteButton.setOnClickListener {
            if (inFavorites) {
                recipeViewModel.removeFromFavorites(meal)
                Toast.makeText(
                    holder.itemView.context,
                    "\"${meal.name}\" removed from Favorites.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                recipeViewModel.addToFavorites(meal)
                Toast.makeText(
                    holder.itemView.context,
                    "\"${meal.name}\" added to Favorites.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun getItemCount(): Int = meals.size
}