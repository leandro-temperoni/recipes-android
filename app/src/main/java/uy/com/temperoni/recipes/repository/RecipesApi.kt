package uy.com.temperoni.recipes.repository

import android.util.Log
import com.google.gson.Gson
import uy.com.temperoni.recipes.dto.Recipe
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class RecipesApi @Inject constructor(private val gson: Gson) {

    fun get(url: String): List<Recipe> {
        return gson.fromJson(getJson(url), RecipeList::class.java).recipes!!
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

    inner class RecipeList {
        val recipes: List<Recipe>? = null
    }
}
