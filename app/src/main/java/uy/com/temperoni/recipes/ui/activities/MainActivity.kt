package uy.com.temperoni.recipes.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import uy.com.temperoni.recipes.ui.compose.chrono.Chronometer
import uy.com.temperoni.recipes.ui.compose.commons.GenericMessage
import uy.com.temperoni.recipes.ui.compose.groceries.Groceries
import uy.com.temperoni.recipes.ui.compose.list.List
import uy.com.temperoni.recipes.ui.compose.navigation.BottomNavBar
import uy.com.temperoni.recipes.ui.compose.navigation.Screen
import uy.com.temperoni.recipes.ui.state.RecipesUiState
import uy.com.temperoni.recipes.ui.state.ScreenState.*
import uy.com.temperoni.recipes.ui.theme.RecetasTheme
import uy.com.temperoni.recipes.viewmodel.RecipesViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
                    val navController = rememberNavController()
                    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                scrollBehavior = scrollBehavior,
                                title = { Text("Recetario") }
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
                            composable(Screen.Groceries.route) {
                                Groceries(viewModel.groceries,
                                    onCheck = { item, newValue ->
                                        viewModel.updateGrocery(
                                            item,
                                            newValue
                                        )
                                    },
                                    onDelete = { item -> viewModel.removeGrocery(item) },
                                    onSave = { item, newValue ->
                                        viewModel.saveGrocery(item, newValue)
                                    },
                                    onAdd = {
                                        viewModel.addGrocery()
                                    }
                                )
                            }
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

    Box {
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