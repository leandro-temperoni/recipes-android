package uy.com.temperoni.recipes.repository.networking

import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.eq
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import uy.com.temperoni.recipes.commons.BaseMockitoInjectTest
import uy.com.temperoni.recipes.dto.RecipeDto
import java.lang.reflect.Type

@OptIn(ExperimentalCoroutinesApi::class)
class RecipesApiTest : BaseMockitoInjectTest() {

    @InjectMocks
    private lateinit var api: RecipesApi

    @Mock
    private lateinit var gsonWrapper: GsonWrapper

    @Mock
    private lateinit var httpWrapper: HttpWrapper

    @Suppress("SENSELESS_COMPARISON")
    @Test
    fun whenCallingGet_shouldReturnTheExpectedDto() = runTest {
        // Arrange
        val dto = RecipeDto()
        `when`(httpWrapper.getJson("url")).thenReturn("response")
        `when`(gsonWrapper.fromJson<RecipeDto>("response")).thenReturn(dto)

        // Act
        val result = api.get<RecipeDto>("url")

        // Assert
        assert(result != null)
        verify(httpWrapper).dispose()
    }

    @Test(expected = Exception::class)
    fun whenCallingGet_withAnExceptionFromGson_shouldReturnThatException() = runTest {
        // Arrange
        `when`(httpWrapper.getJson("url")).thenReturn("response")
        `when`(gsonWrapper.fromJson<RecipeDto>("response")).thenThrow(Exception())

        // Act
        api.get<RecipeDto>("url")

        // Assert
        verify(httpWrapper).dispose()
    }

    @Test(expected = Exception::class)
    fun whenCallingGet_withAnExceptionFromHttpWrapper_shouldReturnThatException() = runTest {
        // Arrange
        `when`(httpWrapper.getJson("url")).thenThrow(Exception())

        // Act
        api.get<RecipeDto>("url")

        // Assert
        verify(httpWrapper).dispose()
    }
}