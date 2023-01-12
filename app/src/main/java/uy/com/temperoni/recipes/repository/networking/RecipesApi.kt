package uy.com.temperoni.recipes.repository.networking

import java.lang.reflect.Type
import javax.inject.Inject

class RecipesApi @Inject constructor(
    private val gsonWrapper: GsonWrapper,
    private val httpWrapper: HttpWrapper
) {

    fun<T> get(urlString: String, classOfT: Class<T>): T {
        return executeGet(urlString) { rawString ->
            return@executeGet parseJsonWithClass(rawString, classOfT)
        }
    }

    fun<T> get(urlString: String, typeOfT: Type): T {
        return executeGet(urlString) { rawString ->
            return@executeGet parseJsonWithType(rawString, typeOfT)
        }
    }

    private fun<T> executeGet(urlString: String, block: (value: String) -> T): T {
        try {
            val rawString = httpWrapper.getJson(urlString)
            return block.invoke(rawString)
        } catch (e: Exception) {
            throw e
        } finally {
            httpWrapper.dispose()
        }
    }

    private fun<T> parseJsonWithType(rawString: String, typeOfT: Type): T {
        return gsonWrapper.fromJson(rawString, typeOfT)
    }

    private fun<T> parseJsonWithClass(rawString: String, classOfT: Class<T>): T {
        return gsonWrapper.fromJson(rawString, classOfT)
    }
}
