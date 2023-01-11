package uy.com.temperoni.recipes.commons

import org.junit.After
import org.junit.Before
import org.mockito.MockitoAnnotations

abstract class BaseMockitoInjectTest {

    private lateinit var closeable: AutoCloseable

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        closeable.close()
    }
}