package uy.com.temperoni.recipes

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import dagger.hilt.android.AndroidEntryPoint
import uy.com.temperoni.recipes.dto.Recipe
import uy.com.temperoni.recipes.ui.state.RecipesUiState
import uy.com.temperoni.recipes.ui.state.ScreenState.LIST
import uy.com.temperoni.recipes.ui.state.ScreenState.LOADING
import uy.com.temperoni.recipes.ui.theme.RecetasTheme
import uy.com.temperoni.recipes.ui.theme.Shapes
import uy.com.temperoni.recipes.viewmodel.RecipesViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: RecipesViewModel by viewModels()

        setContent {
            RecetasTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    RecipesBook(viewModel)
                }
            }
        }

        startActivity(Intent(this, DetailActivity::class.java))
    }
}

@Composable
fun RecipesBook(viewModel: RecipesViewModel) {
    val recipesBook: RecipesUiState by viewModel.getRecipes().collectAsState()

    when (recipesBook.state) {
        LOADING -> {
            Box(modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        LIST -> LazyColumn(Modifier.padding(8.dp, 4.dp)) {
            items(recipesBook.items) { recipe ->
                RecipeRow(recipe)
            }
        }
        else -> {
            // TODO error screen and none
        }
    }
}

@Composable
fun RecipeRow(recipe: Recipe) {
    Surface(elevation = 4.dp, modifier = Modifier
        .height(200.dp)
        .padding(0.dp, 4.dp), shape = Shapes.medium) {
        Image(
            painter = rememberImagePainter(
                data = recipe.image,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth(1f)
        )
        Box(modifier = Modifier.fillMaxWidth(1f), contentAlignment = Alignment.BottomStart) {
            Text(color = MaterialTheme.colors.background, text = recipe.name!!, modifier = Modifier.padding(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RecetasTheme {
        Box(modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}