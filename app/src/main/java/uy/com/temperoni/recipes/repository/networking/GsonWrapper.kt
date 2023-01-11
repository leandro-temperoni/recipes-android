package uy.com.temperoni.recipes.repository.networking

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uy.com.temperoni.recipes.dto.RecipeDto
import java.lang.reflect.Type
import javax.inject.Inject

class GsonWrapper @Inject constructor(private val gson: Gson) {

    fun<T> fromJson(json: String): T {
        val type: Type = object : TypeToken<T>() {}.type
        return gson.fromJson(json, type)
    }
}