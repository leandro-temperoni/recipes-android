package uy.com.temperoni.recipes.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import uy.com.temperoni.recipes.ui.compose.navigation.Screen
import uy.com.temperoni.recipes.ui.compose.commons.Chronometer
import uy.com.temperoni.recipes.ui.compose.commons.InformativeMessage
import uy.com.temperoni.recipes.ui.compose.list.BottomNavBar
import uy.com.temperoni.recipes.ui.compose.list.List
import uy.com.temperoni.recipes.ui.state.RecipesUiState
import uy.com.temperoni.recipes.ui.state.ScreenState.*
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
            // A surface container using the 'background' color from the theme
            Surface(color = MaterialTheme.colors.background) {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(
                            title = { Text("Recetario") }
                        )
                    },
                    bottomBar = {
                        BottomNavBar(navController = navController)
                    }
                ) { innerPadding ->
                    NavHost(navController, startDestination = Screen.Desserts.route, Modifier.padding(innerPadding)) {
                        composable(Screen.Desserts.route) { Content(viewModel, Screen.Desserts) }
                        composable(Screen.Preparations.route) { Content(viewModel, Screen.Preparations) }
                        composable(Screen.Chronometer.route) { Chronometer() }
                    }
                }
            }
        }
    }
}

// TODO use navigation
// https://developer.android.com/codelabs/jetpack-compose-navigation
@ExperimentalPagerApi
fun goToDetail(context: Context, id: Int, title: String) {
    context.startActivity(Intent(context, DetailActivity::class.java).putExtra("id", id).putExtra("title", title))
}

@ExperimentalPagerApi
@Composable
fun Content(viewModel: RecipesViewModel, screen: Screen) {
    val recipesBook: RecipesUiState by viewModel.getRecipes().collectAsState()

    when (recipesBook.state) {
        LIST -> List(recipesBook = recipesBook, screen)
        ZRP -> InformativeMessage(message = "Todavia no has cargado nada aqui")
        ERROR -> InformativeMessage(message = "Ocurrió un error al cargar el recetario")
        else -> {
            // Do nothing
        }
    }
}