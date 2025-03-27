package com.project3.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project3.recipes.R
import com.project3.recipes.repository.RecipeRepository
import com.project3.recipes.ui.adapter.RecipesAdapter
import com.project3.recipes.viewmodel.RecipeViewModel
import com.project3.recipes.viewmodel.RecipeViewModelFactory

class RecipeFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize UI elements
        val recipesRecyclerView = view.findViewById<RecyclerView>(R.id.recipesRecyclerView)
        recipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observe fetched recipes in ViewModel, and update the list of fetched recipes
        recipeViewModel.fetchedRecipes.observe(viewLifecycleOwner) { meals ->
            recipesRecyclerView.adapter = RecipesAdapter(meals, recipeViewModel)
        }

        recipeViewModel.searchForRecipes("")
    }
}