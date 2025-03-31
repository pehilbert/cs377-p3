package com.project3.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project3.recipes.R
import com.project3.recipes.data.database.MealDatabase
import com.project3.recipes.repository.RecipeRepository
import com.project3.recipes.ui.adapter.RecipesAdapter
import com.project3.recipes.viewmodel.RecipeViewModel
import com.project3.recipes.viewmodel.RecipeViewModelFactory

class FavoriteRecipesFragment : Fragment() {
    private val recipeViewModel: RecipeViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            RecipeViewModelFactory(RecipeRepository(MealDatabase.getInstance(requireActivity()).mealDao()))
        ).get(RecipeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favorite_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize RecyclerView
        val recipesRecyclerView = view.findViewById<RecyclerView>(R.id.recipesRecyclerView)
        recipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val noResultsTextView = view.findViewById<TextView>(R.id.no_results_text)

        // Observe fetched recipes in ViewModel, and update the list of fetched recipes
        recipeViewModel.favoriteRecipes.observe(viewLifecycleOwner) { meals ->
            recipesRecyclerView.adapter = RecipesAdapter(meals, recipeViewModel)

            noResultsTextView.visibility =
                if (meals.isNullOrEmpty()) View.VISIBLE else View.GONE
        }
    }
}