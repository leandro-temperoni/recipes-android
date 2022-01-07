package uy.com.temperoni.recipes.repository

import android.content.res.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uy.com.temperoni.recipes.dto.Recipe
import javax.inject.Inject

class RecipesRepository @Inject constructor(private val resources: Resources) {

    fun fetchRecipes(): Flow<List<Recipe>> = flow {
        delay(3000)
        val data = Decoder().decodeJson(resources)!!
        emit(data)
    }.flowOn(Dispatchers.IO)
}
