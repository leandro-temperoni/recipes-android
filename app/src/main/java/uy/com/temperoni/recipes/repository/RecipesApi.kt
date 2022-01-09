package uy.com.temperoni.recipes.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken;
import uy.com.temperoni.recipes.dto.Recipe
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

    fun getRecipesList(): List<Recipe> {
        val userListType: Type = object : TypeToken<ArrayList<Recipe?>?>() {}.getType()
        return gson.fromJson(getJson(API_URL), userListType)
    }

    fun getRecipeDetail(id: Int): Recipe {
        return gson.fromJson(getJson("$API_URL/$id"), Recipe::class.java)
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
                Log.d("Response: ", "> $line");   //here u ll get whole response...... :-)
                line = reader.readLine()
            }

            return buffer.toString();

        } catch (e: Exception) {
            return "{}"
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
