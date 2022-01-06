package uy.com.temperoni.recipes.repository

import android.content.res.Resources
import uy.com.temperoni.recipes.dto.Recipe

class RecipesRepository {

    fun fetchRecipes(resources: Resources): List<Recipe> {
        return Decoder().decodeJson(resources)!!
    }
}
