package uy.com.temperoni.recipes.repository.networking

import com.google.gson.reflect.TypeToken;
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.reflect.Type
import javax.inject.Inject

class RecipesApi @Inject constructor(
    private val gsonWrapper: GsonWrapper,
    private val httpWrapper: HttpWrapper
) {

    suspend fun<T> get(urlString: String): T {
        return suspendCancellableCoroutine { continuation ->
            try {
                val rawString = httpWrapper.getJson(urlString)
                val result = gsonWrapper.fromJson<T>(rawString)
                continuation.resumeWith(Result.success(result))
            } catch (e: Exception) {
                continuation.resumeWith(Result.failure(e))
            } finally {
                httpWrapper.dispose()
            }
        }
    }
}
