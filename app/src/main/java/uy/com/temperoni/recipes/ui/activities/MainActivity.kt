package uy.com.temperoni.recipes.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import uy.com.temperoni.recipes.ui.compose.commons.Chronometer
import uy.com.temperoni.recipes.ui.compose.commons.GenericMessage
import uy.com.temperoni.recipes.ui.compose.groceries.Groceries
import uy.com.temperoni.recipes.ui.compose.list.List
import uy.com.temperoni.recipes.ui.compose.navigation.BottomNavBar
import uy.com.temperoni.recipes.ui.compose.navigation.Screen
import uy.com.temperoni.recipes.ui.state.RecipesUiState
import uy.com.temperoni.recipes.ui.state.ScreenState
import uy.com.temperoni.recipes.ui.state.ScreenState.*
import uy.com.temperoni.recipes.ui.theme.RecetasTheme
import uy.com.temperoni.recipes.viewmodel.RecipesViewModel

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: RecipesViewModel by viewModels()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading()
            }
        }

        setContent {
            RecetasTheme {
                Surface {
                    val scaffoldState = rememberScaffoldState()
                    val navController = rememberNavController()
                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            TopAppBar(
                                title = { Text("Recetario") },
                                backgroundColor = MaterialTheme.colors.surface,
                                elevation = 0.dp
                            )
                        },
                        bottomBar = {
                            BottomNavBar(navController = navController)
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController,
                            startDestination = Screen.Desserts.route,
                            Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.Desserts.route) { Content(viewModel) }
                            composable(Screen.Groceries.route) { Groceries() }
                            composable(Screen.Chronometer.route) { Chronometer() }
                        }
                    }
                }
            }
        }
    }
}

// TODO use navigation
// https://developer.android.com/codelabs/jetpack-compose-navigation
@ExperimentalPagerApi
fun goToDetail(context: Context, id: String, title: String) {
    context.startActivity(
        Intent(context, DetailActivity::class.java).putExtra("id", id).putExtra("title", title)
    )
}

@ExperimentalPagerApi
@Composable
fun Content(viewModel: RecipesViewModel) {
    val recipesBook: RecipesUiState by viewModel.getRecipes().collectAsState()

    Box(modifier = Modifier.background(MaterialTheme.colors.surface)) {
        when (recipesBook.state) {
            SUCCESS -> List(recipes = recipesBook.desserts)
            ZRP -> GenericMessage(message = "No has cargado contenido aquí")
            ERROR -> GenericMessage(message = "Ocurrió un error al cargar el recetario")
            else -> {
                // Do nothing
            }
        }
    }
}