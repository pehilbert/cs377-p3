package com.project3.recipes.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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

class RecipeFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize UI elements
        val recipesRecyclerView = view.findViewById<RecyclerView>(R.id.recipesRecyclerView)
        recipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val noResultsTextView = view.findViewById<TextView>(R.id.no_results_text)
        val loadingSpinner = view.findViewById<ProgressBar>(R.id.loading_spinner)
        val searchBar = view.findViewById<SearchView>(R.id.recipe_search_bar)

        // Show loading spinner when ViewModel is loading
        recipeViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            loadingSpinner.visibility = if (loading) View.VISIBLE else View.GONE
        }

        // Show an error Toast when there is an API error
        recipeViewModel.apiError.observe(viewLifecycleOwner) { errorOccurred ->
            if (errorOccurred) {
                Toast.makeText(view.context, "There was an error fetching recipes.", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe fetched recipes in ViewModel, and update the list of fetched recipes
        val recipesAdapter = RecipesAdapter(
            recipeViewModel.fetchedRecipes.value ?: emptyList(),
            recipeViewModel
        )
        recipesRecyclerView.adapter = recipesAdapter

        recipeViewModel.fetchedRecipes.observe(viewLifecycleOwner) { meals ->
            recipesAdapter.setMeals(meals)

            noResultsTextView.visibility =
                if (meals.isNullOrEmpty()) View.VISIBLE else View.GONE
        }

        // Update favorite statuses when changed
        recipeViewModel.favoriteRecipes.observe(viewLifecycleOwner) { _ ->
            recipesAdapter.notifyDataSetChanged()
        }

        // dynamically search for recipes based on input in the SearchView
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // clean search query, ensure the user actually typed something
                val trimmed = query?.trim()
                if (trimmed != null) {
                    Log.d("RecipeFragment", "Submitted search for: $trimmed")

                    // update fetched recipes
                    recipeViewModel.searchForRecipes(trimmed)

                    // clear keyboard
                    searchBar.clearFocus()
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}