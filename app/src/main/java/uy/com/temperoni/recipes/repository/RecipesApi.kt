package uy.com.temperoni.recipes.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken;
import kotlinx.coroutines.suspendCancellableCoroutine
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
        const val API_URL = "https://2kxenzmhnf.execute-api.us-east-1.amazonaws.com/recipes"
    }

    suspend fun getRecipesList(): List<RecipeDto> {
        val userListType: Type = object : TypeToken<ArrayList<RecipeDto?>?>() {}.type
        val rawString = getJson(API_URL)
        return gson.fromJson(rawString, userListType)
    }

    suspend fun getRecipeDetail(id: String): RecipeDto {
        val rawString = getJson("${API_URL}/$id")
        return gson.fromJson(rawString, RecipeDto::class.java)
    }

    private suspend fun getJson(urlString: String): String {
        return suspendCancellableCoroutine { continuation ->

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
                    buffer.append(line + "\n");
                    line = reader.readLine()
                }

                val result = buffer.toString()
                continuation.resumeWith(Result.success(result))

            } catch (e: Exception) {
                continuation.resumeWith(Result.failure(e))
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
}
