package uy.com.temperoni.recipes.viewmodel

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uy.com.temperoni.recipes.repository.RecipesRepository
import uy.com.temperoni.recipes.ui.state.UiState
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val repository: RecipesRepository
) : ViewModel() {

    private var recipesBookState: MutableStateFlow<UiState>? = null

    fun getRecipes(): MutableStateFlow<UiState> {
        if (recipesBookState == null) {
            loadRecipes()
            recipesBookState = MutableStateFlow(UiState())
        }
        return recipesBookState!!
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            repository.fetchRecipes()
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