package uy.com.temperoni.recipes.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken;
import uy.com.temperoni.recipes.dto.RecipeDto
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class RecipesApi @Inject constructor(private val gson: Gson) {

    companion object {
        const val API_URL = "https://tempe-recipes-api.herokuapp.com/recipes"
    }

    fun getRecipesList(): List<RecipeDto> {
        val userListType: Type = object : TypeToken<ArrayList<RecipeDto?>?>() {}.getType()
        return gson.fromJson(getJson(API_URL), userListType)
    }

    fun getRecipeDetail(id: Int): RecipeDto {
        return gson.fromJson(getJson("${API_URL}/$id"), RecipeDto::class.java)
    }

    private fun getJson(urlString: String): String {
        var connection: HttpURLConnection? = null
        var reader: BufferedReader? = null

        try {
            val url = URL(urlString)
            connection = url.openConnection() as? HttpURLConnection
            connection?.connect()

            val stream: InputStream? = connection?.inputStream

            reader = BufferedReader(InputStreamReader(stream))

            val buffer = StringBuffer()
            var line = reader.readLine()

            while (line != null) {
                buffer.append(line+"\n");
                line = reader.readLine()
            }

            return buffer.toString();

        } catch (e: Exception) {
            throw IOException()
        } finally {
            connection?.disconnect()
            try {
                reader?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
