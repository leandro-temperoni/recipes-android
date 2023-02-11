package uy.com.temperoni.recipes.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import uy.com.temperoni.recipes.ui.compose.commons.GenericMessage
import uy.com.temperoni.recipes.ui.compose.commons.Loading
import uy.com.temperoni.recipes.ui.compose.detail.DetailImage
import uy.com.temperoni.recipes.ui.compose.detail.DetailSummary
import uy.com.temperoni.recipes.ui.compose.detail.DetailTabs
import uy.com.temperoni.recipes.ui.model.Recipe
import uy.com.temperoni.recipes.ui.state.RecipeDetailUiState
import uy.com.temperoni.recipes.ui.state.ScreenState.*
import uy.com.temperoni.recipes.ui.theme.RecetasTheme
import uy.com.temperoni.recipes.viewmodel.RecipeDetailViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalPagerApi
@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: RecipeDetailViewModel by viewModels()

        setContent {
            RecetasTheme {
                val toolbarHeight = 56.dp
                val toolbarHeightPx =
                    with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
                val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
                val nestedScrollConnection = remember {
                    CustomNestedScrollConnection(toolbarHeightPx, toolbarOffsetHeightPx)
                }

                Surface(color = MaterialTheme.colorScheme.background) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            // attach as a parent to the nested scroll system
                            .nestedScroll(nestedScrollConnection)
                    ) {
                        Content(intent.getStringExtra("id") ?: "", viewModel)
                        TopAppBar(
                            title = {},
                            navigationIcon = {
                                IconButton(
                                    onClick = { finish() }
                                ) {
                                    Icon(
                                        Icons.Filled.ArrowBack,
                                        contentDescription = "Localized description"
                                    )
                                }
                            },
                            modifier = Modifier
                                .height(toolbarHeight)
                                .offset {
                                    IntOffset(
                                        x = 0,
                                        y = toolbarOffsetHeightPx.value.roundToInt()
                                    )
                                }
                        )
                    }
                }
            }
        }
    }

    private class CustomNestedScrollConnection(
        private val toolbarHeightPx: Float,
        private val toolbarOffsetHeightPx: MutableState<Float>
    ) : NestedScrollConnection {
        override fun onPreScroll(
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            val delta = available.y
            val newOffset = toolbarOffsetHeightPx.value + delta
            toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)

            return Offset.Zero
        }
    }
}

@ExperimentalPagerApi
@Composable
fun Content(id: String, viewModel: RecipeDetailViewModel) {
    val recipe: RecipeDetailUiState by viewModel.getRecipe(id).collectAsState()

    when (recipe.state) {
        LOADING -> Loading()
        SUCCESS -> Detail(recipe.item)
        ERROR -> GenericMessage(message = "Ocurrió un error al cargar la receta")
        else -> {
            // Do nothing
        }
    }
}

@ExperimentalPagerApi
@Composable
fun Detail(recipe: Recipe) {
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f)
            .verticalScroll(ScrollState(0))
    ) {
        DetailImage(urls = recipe.images)

        DetailSummary(name = recipe.name, recipe.introduction)

        DetailTabs(recipe)
    }
}