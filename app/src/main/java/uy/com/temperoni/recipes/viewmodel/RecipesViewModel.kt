package uy.com.temperoni.recipes.viewmodel

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uy.com.temperoni.recipes.dto.Recipe
import uy.com.temperoni.recipes.repository.RecipesRepository

class RecipesViewModel : ViewModel() {

    private val repository = RecipesRepository()

    private var recipes: MutableLiveData<List<Recipe>>? = null

    fun getRecipes(resources: Resources): LiveData<List<Recipe>> {
        if (recipes == null) {
            loadRecipes(resources)
            recipes = MutableLiveData<List<Recipe>>()
        }
        return recipes!!
    }

    private fun loadRecipes(resources: Resources) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            val response = repository.fetchRecipes(resources)
            withContext(Dispatchers.Main) {
                recipes!!.value = response
            }
        }
    }
}