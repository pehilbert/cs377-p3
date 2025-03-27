package com.project3.recipes.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
    private val meals: List<Meal>,
    private val recipeViewModel: RecipeViewModel
) : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {
    init {
        Log.d("RecipesAdapter", "meals: ${meals.map {it.name}}")
    }

    class RecipesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeName: TextView = view.findViewById(R.id.recipe_name)
        val thumbnail: ImageView = view.findViewById(R.id.recipe_thumbnail)
        val detailsButton: Button = view.findViewById(R.id.details_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return RecipesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val meal = meals[position]
        holder.recipeName.text = meal.name
        holder.thumbnail.load(meal.thumbnail)

        holder.detailsButton.setOnClickListener {
            recipeViewModel.setRecipeDetails(meal)
            holder.itemView.findNavController().navigate(R.id.nav_recipe_details)
        }
    }

    override fun getItemCount(): Int = meals.size
}