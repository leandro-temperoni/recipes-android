package uy.com.temperoni.recipes.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import uy.com.temperoni.recipes.dto.RecipeDto
import uy.com.temperoni.recipes.repository.datastore.GroceriesList
import uy.com.temperoni.recipes.repository.datastore.RecipesDataStoreKeys
import uy.com.temperoni.recipes.repository.networking.RecipesApi
import java.io.IOException
import java.lang.reflect.Type
import javax.inject.Inject

class RecipesRepository @Inject constructor(
    private val api: RecipesApi,
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        const val API_URL = "https://2kxenzmhnf.execute-api.us-east-1.amazonaws.com/recipes"
    }

    suspend fun fetchRecipes(): Flow<List<RecipeDto>> = flow {
        val type: Type = object : TypeToken<List<RecipeDto>>() {}.type
        val data: List<RecipeDto> = api.get(API_URL, type)
        emit(data)
    }.flowOn(Dispatchers.IO)

    fun fetchRecipe(id: String): RecipeDto {
        return api.get("$API_URL/$id", RecipeDto::class.java)
    }

    private val groceriesListFlow: Flow<GroceriesList> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { data ->
            val list = data[RecipesDataStoreKeys.GROCERIES_LIST]?: ""
            GroceriesList(list)
        }

    suspend fun fetchGroceries() {
        groceriesListFlow.collect()
    }

    suspend fun saveGroceries(list: List<String>) {
        dataStore.edit { preferences ->
            preferences[RecipesDataStoreKeys.GROCERIES_LIST] = ""
        }
    }
}
