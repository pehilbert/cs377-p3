package com.project3.recipes.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
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

        val searchBar = view.findViewById<EditText>(R.id.recipe_search_bar)

        // Observe fetched recipes in ViewModel, and update the list of fetched recipes
        recipeViewModel.fetchedRecipes.observe(viewLifecycleOwner) { meals ->
            recipesRecyclerView.adapter = RecipesAdapter(meals, recipeViewModel)
        }

        recipeViewModel.searchForRecipes(searchBar.text.toString())

        searchBar.addTextChangedListener { object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            // only search after the user is done typing to avoid spamming API
            override fun afterTextChanged(searchTerm: Editable?) {
                Log.d("RecipeFragment", "Searching for: $searchTerm")
                recipeViewModel.searchForRecipes(searchTerm.toString().trim())
            }
        }}
    }
}