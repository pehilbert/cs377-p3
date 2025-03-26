package com.project3.recipes.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project3.recipes.R
import com.project3.recipes.data.model.Meal

class RecipesAdapter(
    private val meals: List<Meal>
) : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {
    init {
        Log.d("RecipesAdapter", "meals: ${meals.map {it.name}}")
    }
    class RecipesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeName: TextView = view.findViewById(R.id.recipe_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return RecipesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val meal = meals[position]
        holder.recipeName.text = meal.name
    }

    override fun getItemCount(): Int = meals.size
}