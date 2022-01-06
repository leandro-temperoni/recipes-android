package uy.com.temperoni.recipes.viewmodel

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uy.com.temperoni.recipes.dto.Recipe
import uy.com.temperoni.recipes.repository.RecipesRepository
import uy.com.temperoni.recipes.ui.state.UiState

class RecipesViewModel : ViewModel() {

    private val repository = RecipesRepository()

    private var recipesBookState: MutableStateFlow<UiState>? = null

    fun getRecipes(resources: Resources): MutableStateFlow<UiState> {
        if (recipesBookState == null) {
            loadRecipes(resources)
            recipesBookState = MutableStateFlow(UiState())
        }
        return recipesBookState!!
    }

    private fun loadRecipes(resources: Resources) {
        viewModelScope.launch {
            repository.fetchRecipes(resources)
                .catch {
                    // TODO add error case
                }
                .collect { response ->
                    recipesBookState!!.value = UiState().apply {
                        items = response
                        state = UiState.ScreenState.LIST
                    }
                }
        }
    }
}