package uy.com.temperoni.recipes.repository.networking

import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.reflect.Type
import javax.inject.Inject

class RecipesApi @Inject constructor(
    private val gsonWrapper: GsonWrapper,
    private val httpWrapper: HttpWrapper
) {

    suspend fun<T> get(urlString: String, classOfT: Class<T>): T {
        return executeGet(urlString) { rawString ->
            return@executeGet parseJsonWithClass(rawString, classOfT)
        }
    }

    suspend fun<T> get(urlString: String, typeOfT: Type): T {
        return executeGet(urlString) { rawString ->
            return@executeGet parseJsonWithType(rawString, typeOfT)
        }
    }

    private suspend fun<T> executeGet(urlString: String, block: (value: String) -> T): T {
        return suspendCancellableCoroutine { continuation ->
            try {
                val rawString = httpWrapper.getJson(urlString)
                val result = block.invoke(rawString)
                continuation.resumeWith(Result.success(result))
            } catch (e: Exception) {
                continuation.resumeWith(Result.failure(e))
            } finally {
                httpWrapper.dispose()
            }
        }
    }

    private fun<T> parseJsonWithType(rawString: String, typeOfT: Type): T {
        return gsonWrapper.fromJson(rawString, typeOfT)
    }

    private fun<T> parseJsonWithClass(rawString: String, classOfT: Class<T>): T {
        return gsonWrapper.fromJson(rawString, classOfT)
    }
}
