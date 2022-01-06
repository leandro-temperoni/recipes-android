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
import uy.com.temperoni.recipes.ui.state.UiState

class RecipesViewModel : ViewModel() {

    private val repository = RecipesRepository()

    private var recipesBookState: MutableLiveData<UiState>? = null

    fun getRecipes(resources: Resources): LiveData<UiState> {
        if (recipesBookState == null) {
            loadRecipes(resources)
            recipesBookState = MutableLiveData()
        }
        return recipesBookState!!
    }

    private fun loadRecipes(resources: Resources) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)
            val response = repository.fetchRecipes(resources)
            withContext(Dispatchers.Main) {
                recipesBookState!!.value = UiState().apply {
                    items = response
                    state = UiState.ScreenState.LIST
                }
            }
        }
    }
}