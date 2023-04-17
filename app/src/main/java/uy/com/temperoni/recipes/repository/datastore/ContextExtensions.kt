package uy.com.temperoni.recipes.repository.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.recipesDataStore: DataStore<Preferences> by preferencesDataStore(
    // just my preference of naming including the package name
    name = "recipes_data_store"
)