package uy.com.temperoni.recipes.repository

import android.content.res.Resources
import com.google.gson.Gson
import uy.com.temperoni.recipes.dto.Recipe
import java.io.IOException
import java.io.InputStream

class Decoder {

    fun decodeJson(resources: Resources): List<Recipe>? {
        return Gson().fromJson(getJson(resources), RecipeList::class.java).recipes
    }

    private fun getJson(resources: Resources): String {
        var res = ""
        try {
            val inputStream: InputStream = resources.assets.open("recipes.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            res = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return res
    }

    private inner class RecipeList {
        val recipes: List<Recipe>? = null
    }
}
