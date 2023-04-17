package uy.com.temperoni.recipes.repository.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object RecipesDataStoreKeys {
    val GROCERIES_LIST = stringPreferencesKey("groceries_list")
}