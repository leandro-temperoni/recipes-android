package uy.com.temperoni.recipes.repository.networking

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

class GsonWrapper @Inject constructor(private val gson: Gson) {

    fun <T> fromJson(json: String, classOfT: Class<T>): T {
        return gson.fromJson(json, classOfT)
    }

    fun <T> fromJson(json: String, typeOfT: Type): T {
        return gson.fromJson(json, typeOfT)
    }
}