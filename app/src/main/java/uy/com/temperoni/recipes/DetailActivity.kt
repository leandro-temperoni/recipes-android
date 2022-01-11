package uy.com.temperoni.recipes

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import dagger.hilt.android.AndroidEntryPoint
import uy.com.temperoni.recipes.dto.Ingredient
import uy.com.temperoni.recipes.dto.InstructionItem
import uy.com.temperoni.recipes.dto.Recipe
import uy.com.temperoni.recipes.ui.state.RecipeDetailUiState
import uy.com.temperoni.recipes.ui.state.ScreenState
import uy.com.temperoni.recipes.ui.theme.RecetasTheme
import uy.com.temperoni.recipes.viewmodel.RecipeDetailViewModel
import kotlin.math.roundToInt

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
                    object : NestedScrollConnection {
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

                Surface(color = MaterialTheme.colors.background) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            // attach as a parent to the nested scroll system
                            .nestedScroll(nestedScrollConnection)
                    ) {
                        Content(intent.getIntExtra("id", -1), viewModel)
                        TopAppBar(
                            title = {},
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        finish()
                                    }
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
}

@Composable
fun Content(id: Int, viewModel: RecipeDetailViewModel) {
    val recipe: RecipeDetailUiState by viewModel.getRecipe(id).collectAsState()

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
            Detail(recipe.item)
        }
        else -> {
            // Do nothing
        }
    }
}

// TODO separate composables in different files
@Composable
fun Detail(recipe: Recipe) {
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f)
            .verticalScroll(ScrollState(0))
    ) {
        Image(
            painter = rememberImagePainter(
                data = recipe.image,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(350.dp)
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f)
        ) {
            Column {
                Text(
                    color = MaterialTheme.colors.onSurface,
                    text = recipe.name!!,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 32.sp
                )

                SubTitle("DESCRIPCION")

                Text(
                    color = MaterialTheme.colors.onSurface,
                    text = recipe.introduction!!,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 16.sp
                )

                Tabs(recipe)
            }
        }
    }
}

@Composable
fun SubTitle(text: String) {
    Text(
        color = MaterialTheme.colors.primary,
        text = text.toUpperCase(Locale.current),
        modifier = Modifier.padding(8.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun Ingredient(data: Ingredient) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(12.dp), verticalAlignment = Alignment.Top
    ) {
        Text(
            color = MaterialTheme.colors.onSurface,
            text = "${data.name}",
            modifier = Modifier.weight(.6f),
            fontSize = 16.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            color = MaterialTheme.colors.primary,
            text = "${data.amount}",
            modifier = Modifier.weight(.4f),
            textAlign = TextAlign.End,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun Step(text: String, index: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(12.dp), verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colors.secondary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                color = MaterialTheme.colors.primaryVariant,
                text = "${index + 1}",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Text(
            color = MaterialTheme.colors.onSurface,
            text = text,
            modifier = Modifier.padding(12.dp, 0.dp, 0.dp, 0.dp),
            fontSize = 16.sp,
        )
    }
}

@Composable
fun Ingredients(ingredients: List<Ingredient?>) {
    ingredients.forEach { ingredient ->
        Ingredient(data = ingredient!!)
    }
}

@Composable
fun Steps(instructions: List<InstructionItem?>) {
    instructions.forEach { instructionItem ->
        if (!instructionItem?.description.isNullOrBlank()) {
            SubTitle(instructionItem?.description!!)
        }
        instructionItem?.steps?.forEachIndexed { index, step ->
            Step(step!!, index)
        }
    }
}

private enum class DetailsTabs(val text: String, val icon: Int) {
    Ingredients("INGREDIENTES", R.drawable.ic_baseline_kitchen_24),
    Steps("PASOS", R.drawable.ic_baseline_format_list_numbered_24)
}

@Composable
fun Tabs(recipe: Recipe) {
    var state by remember { mutableStateOf(0) }
    TabRow(selectedTabIndex = state) {
        DetailsTabs.values().forEachIndexed { index, tab ->
            Tab(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = tab.icon),
                        contentDescription = ""
                    )
                },
                text = { Text(tab.text) },
                selected = state == index,
                onClick = { state = index },
                selectedContentColor = Color.White
            )
        }
    }

    when (state) {
        0 -> Ingredients(ingredients = recipe.ingredients!!)
        1 -> Steps(instructions = recipe.instructions!!)
        else -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    RecetasTheme {
        Column {
            Ingredient(Ingredient("300 gr", "Harina"))
            Ingredient(Ingredient("3", "Huevos"))
            Ingredient(Ingredient("150", "Azucar impalpable"))
            Ingredient(Ingredient("2 cdtas", "Polvo de hornear"))
            Ingredient(Ingredient("1 pizca", "Sal"))
            Ingredient(
                Ingredient(
                    "1 pizca",
                    "Algo largo largoAlgo largo largoAlgo largo largoAlgo largo largo"
                )
            )
            Ingredient(
                Ingredient(
                    "1 pizca",
                    "largo largoAlgo largo largoAlgo largo largoAlgo largo largoAlgo largo largoAlgo largo largoAlgo largo largoAlgo largo largo"
                )
            )
        }
    }
}