package uy.com.temperoni.recipes

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import uy.com.temperoni.recipes.ui.state.RecipeDetailUiState
import uy.com.temperoni.recipes.ui.state.ScreenState
import uy.com.temperoni.recipes.ui.theme.RecetasTheme
import uy.com.temperoni.recipes.viewmodel.RecipeDetailViewModel

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: RecipeDetailViewModel by viewModels()

        setContent {
            RecetasTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(viewModel: RecipeDetailViewModel) {
    val recipe: RecipeDetailUiState by viewModel.getRecipe(2).collectAsState()

    when (recipe.state) {
        ScreenState.LOADING -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1f), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        ScreenState.DETAIL -> {
            Text(text = recipe.item.name!!)
        }
        else -> {
            // Do nothing
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    RecetasTheme {
        //Greeting("Android")
    }
}