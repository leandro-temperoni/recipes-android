package uy.com.temperoni.recipes.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import dagger.hilt.android.AndroidEntryPoint
import uy.com.temperoni.recipes.ui.compose.commons.Loading
import uy.com.temperoni.recipes.ui.compose.list.List
import uy.com.temperoni.recipes.ui.state.RecipesUiState
import uy.com.temperoni.recipes.ui.state.ScreenState.LIST
import uy.com.temperoni.recipes.ui.state.ScreenState.LOADING
import uy.com.temperoni.recipes.ui.theme.RecetasTheme
import uy.com.temperoni.recipes.viewmodel.RecipesViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: RecipesViewModel by viewModels()

        setContent {
            RecetasTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val scaffoldState = rememberScaffoldState()
                    val scope = rememberCoroutineScope()
                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            TopAppBar(
                                title = { Text("Recetario") }
                            )
                        },
                        content = {
                            Content(viewModel)
                        }
                    )
                }
            }
        }
    }
}

// TODO use navigation
// https://developer.android.com/codelabs/jetpack-compose-navigation
fun goToDetail(context: Context, id: Int, title: String) {
    context.startActivity(Intent(context, DetailActivity::class.java).putExtra("id", id).putExtra("title", title))
}

@Composable
fun Content(viewModel: RecipesViewModel) {
    val recipesBook: RecipesUiState by viewModel.getRecipes().collectAsState()

    when (recipesBook.state) {
        LOADING -> Loading()
        LIST -> List(recipesBook = recipesBook)
        else -> {
            // TODO error screen and none
        }
    }
}