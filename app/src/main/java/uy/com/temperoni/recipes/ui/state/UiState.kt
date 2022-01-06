package uy.com.temperoni.recipes.ui.state

import uy.com.temperoni.recipes.dto.Recipe

class UiState {

    var items: List<Recipe> = listOf()
    var state: ScreenState = ScreenState.LOADING

    enum class ScreenState {
        LOADING, LIST, ERROR
    }
}