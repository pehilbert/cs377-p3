package com.project3.recipes

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project3.recipes.databinding.ActivityMainBinding
import com.project3.recipes.repository.RecipeRepository
import com.project3.recipes.viewmodel.RecipeViewModel
import com.project3.recipes.viewmodel.RecipeViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialize values
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipeViewModel = ViewModelProvider(
            this,
            RecipeViewModelFactory(RecipeRepository())
        ).get(RecipeViewModel::class.java)

        setSupportActionBar(binding.appBarMain.toolbar)

        // set up navigation
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_recipe, R.id.nav_favorite_recipes
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // populate ViewModel with some initial recipes
        recipeViewModel.searchForRecipes("")
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}