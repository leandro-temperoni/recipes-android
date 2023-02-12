package uy.com.temperoni.recipes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uy.com.temperoni.recipes.domain.GetRecipesBookUseCase
import uy.com.temperoni.recipes.mappers.RecipesMapper
import uy.com.temperoni.recipes.repository.RecipesRepository
import uy.com.temperoni.recipes.ui.model.Grocery
import uy.com.temperoni.recipes.ui.state.RecipesUiState
import uy.com.temperoni.recipes.ui.state.ScreenState
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val repository: RecipesRepository,
    private val mapper: RecipesMapper,
    private val getRecipesBookUseCase: GetRecipesBookUseCase
) : ViewModel() {

    private var recipesBookStateRecipes: MutableStateFlow<RecipesUiState>? = null

    private var groceries: MutableStateFlow<List<Grocery>> = MutableStateFlow(listOf(Grocery("Manteca"), Grocery("Azucar"), Grocery("Leche"), Grocery("Cacao")))
    private val _groceries: StateFlow<List<Grocery>> = groceries.asStateFlow()

    init {
        getRecipes()
    }

    fun getRecipes(): MutableStateFlow<RecipesUiState> {
        if (recipesBookStateRecipes == null) {
            recipesBookStateRecipes = MutableStateFlow(RecipesUiState())
            loadRecipes()
        }
        return recipesBookStateRecipes!!
    }

    fun isLoading(): Boolean {
        return recipesBookStateRecipes!!.value.state == ScreenState.LOADING
    }

    private fun loadRecipes() {
        viewModelScope.launch(Dispatchers.Main) {
            repository.fetchRecipes()
                .catch {
                    recipesBookStateRecipes!!.value = getRecipesBookUseCase()
                }
                .map { response ->
                    mapper.mapRecipes(response)
                }
                .collect { response ->
                    recipesBookStateRecipes!!.value = getRecipesBookUseCase(response)
                }
        }
    }

    fun getGroceries(): StateFlow<List<Grocery>> {
        return _groceries
    }

    fun updateGrocery(grocery: Grocery, isChecked: Boolean) {
        grocery.checked.value = isChecked
    }
}