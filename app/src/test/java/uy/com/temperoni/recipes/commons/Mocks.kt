package uy.com.temperoni.recipes.commons

import uy.com.temperoni.recipes.ui.model.Recipe

fun mockRecipe(id: String) = Recipe(
    id = id,
    images = listOf(""),
    introduction = "",
    name = "name",
    ingredients = emptyList(),
    instructions = emptyList()
)