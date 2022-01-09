package uy.com.temperoni.recipes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uy.com.temperoni.recipes.repository.RecipesRepository
import uy.com.temperoni.recipes.ui.state.RecipesUiState
import uy.com.temperoni.recipes.ui.state.ScreenState
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val repository: RecipesRepository
) : ViewModel() {

    private var recipesBookStateRecipes: MutableStateFlow<RecipesUiState>? = null

    fun getRecipes(): MutableStateFlow<RecipesUiState> {
        if (recipesBookStateRecipes == null) {
            loadRecipes()
            recipesBookStateRecipes = MutableStateFlow(RecipesUiState())
        }
        return recipesBookStateRecipes!!
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            repository.fetchRecipes()
                .catch {
                    // TODO add error case
                }
                .collect { response ->
                    recipesBookStateRecipes!!.value = RecipesUiState().apply {
                        items = response
                        state = ScreenState.LIST
                    }
                }
        }
    }
}