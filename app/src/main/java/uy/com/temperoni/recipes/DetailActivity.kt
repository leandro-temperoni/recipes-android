package uy.com.temperoni.recipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
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
            Detail(recipe.item)
        }
        else -> {
            // Do nothing
        }
    }
}

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
                .fillMaxHeight(1f), shape = RoundedCornerShape(4.dp, 4.dp, 0.dp, 0.dp)
        ) {
            Column {
                Text(
                    color = Color.Black,
                    text = recipe.name!!,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    color = Color.Gray,
                    text = recipe.introduction!!,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 14.sp
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
    Row(modifier = Modifier
        .fillMaxWidth(1f)
        .padding(12.dp), verticalAlignment = Alignment.Top) {
        Text(
            color = Color.Black,
            text = "${data.name}",
            modifier = Modifier.weight(.75f),
            fontSize = 16.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            color = MaterialTheme.colors.primary,
            text = "${data.amount}",
            modifier = Modifier.weight(.25f),
            textAlign = TextAlign.End,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun Step(text: String, index: Int) {
    Row(modifier = Modifier
        .fillMaxWidth(1f)
        .padding(0.dp, 12.dp), verticalAlignment = Alignment.Top) {
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
            color = Color.Black,
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

@Composable
fun Tabs(recipe: Recipe) {
    var state by remember { mutableStateOf(0) }
    TabRow(selectedTabIndex = state) {
        Tab(
            icon = { Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "") },
            text = { Text("INGREDIENTES") },
            selected = state == 0,
            onClick = { state = 0 }
        )
        Tab(
            icon = { Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "") },
            text = { Text("PASOS") },
            selected = state == 1,
            onClick = { state = 1 }
        )
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
            Ingredient(Ingredient("300 gr",  "Harina"))
            Ingredient(Ingredient("3", "Huevos"))
            Ingredient(Ingredient("150",  "Azucar impalpable"))
            Ingredient(Ingredient("2 cdtas",  "Polvo de hornear"))
            Ingredient(Ingredient("1 pizca",  "Sal"))
            Ingredient(Ingredient("1 pizca",  "Algo largo largoAlgo largo largoAlgo largo largoAlgo largo largo"))
            Ingredient(Ingredient("1 pizca",  "largo largoAlgo largo largoAlgo largo largoAlgo largo largoAlgo largo largoAlgo largo largoAlgo largo largoAlgo largo largo"))
        }
    }
}