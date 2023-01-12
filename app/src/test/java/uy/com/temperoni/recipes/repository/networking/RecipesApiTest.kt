package uy.com.temperoni.recipes.repository.networking

import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import uy.com.temperoni.recipes.commons.BaseMockitoInjectTest
import uy.com.temperoni.recipes.dto.RecipeDto
import java.lang.reflect.Type

class RecipesApiTest : BaseMockitoInjectTest() {

    @InjectMocks
    private lateinit var api: RecipesApi

    @Mock
    private lateinit var gsonWrapper: GsonWrapper

    @Mock
    private lateinit var httpWrapper: HttpWrapper

    @Suppress("SENSELESS_COMPARISON")
    @Test
    fun whenCallingGet_withType_shouldReturnTheExpectedDto() {
        // Arrange
        val dto = listOf(RecipeDto())
        `when`(httpWrapper.getJson("url")).thenReturn("response")
        val type: Type = object : TypeToken<List<RecipeDto>>() {}.type
        `when`(gsonWrapper.fromJson<List<RecipeDto>>("response", type)).thenReturn(dto)

        // Act
        val result = api.get<List<RecipeDto>>("url", type)

        // Assert
        assert(result != null)
        verify(httpWrapper).dispose()
    }

    @Suppress("SENSELESS_COMPARISON")
    @Test
    fun whenCallingGet_withClass_shouldReturnTheExpectedDto() {
        // Arrange
        val dto = RecipeDto()
        `when`(httpWrapper.getJson("url")).thenReturn("response")
        `when`(gsonWrapper.fromJson("response", RecipeDto::class.java)).thenReturn(dto)

        // Act
        val result = api.get("url", RecipeDto::class.java)

        // Assert
        assert(result != null)
        verify(httpWrapper).dispose()
    }

    @Test(expected = Exception::class)
    fun whenCallingGet_withAnExceptionFromGson_shouldReturnThatException() {
        // Arrange
        `when`(httpWrapper.getJson("url")).thenReturn("response")
        val type: Type = object : TypeToken<List<RecipeDto>>() {}.type
        `when`(gsonWrapper.fromJson<List<RecipeDto>>("response", type)).thenThrow(Exception())

        // Act
        api.get<RecipeDto>("url", type)

        // Assert
        verify(httpWrapper).dispose()
    }

    @Test(expected = Exception::class)
    fun whenCallingGet_withAnExceptionFromHttpWrapper_shouldReturnThatException() {
        // Arrange
        `when`(httpWrapper.getJson("url")).thenThrow(Exception())

        // Act
        api.get("url", RecipeDto::class.java)

        // Assert
        verify(httpWrapper).dispose()
    }
}