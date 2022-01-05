package uy.com.temperoni.recipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uy.com.temperoni.recipes.ui.theme.RecetasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val list = arrayListOf("Cheesecake de frutos rojos", "Budin de banana", "Carrot cake", "Galletas de vainilla")
        setContent {
            RecetasTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    RecipesList(list)
                }
            }
        }
    }
}

@Composable
fun RecipesList(list: ArrayList<String>) {
    LazyColumn(Modifier.padding(8.dp, 4.dp)) {
        items(list) { recipe ->
            RecipeRow(recipe)
        }
    }
}

@Composable
fun RecipeRow(name: String) {
    Surface(elevation = 4.dp, modifier = Modifier.height(200.dp).padding(0.dp, 4.dp)) {
        Image(bitmap = ImageBitmap.imageResource(id = R.mipmap.foto), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier
            .fillMaxWidth(1f))
        Box(modifier = Modifier.fillMaxWidth(1f), contentAlignment = Alignment.BottomStart) {
            Text(color = MaterialTheme.colors.background, text = name, modifier = Modifier.padding(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val list = arrayListOf("Cheesecake de frutos rojos", "Budin de banana", "Carrot cake", "Galletas de vaiilla", "Galletas de vaiilla", "Galletas de vaiilla", "Galletas de vaiilla", "Galletas de chocolate")
    RecetasTheme {
        Surface(color = MaterialTheme.colors.background) {
            RecipesList(list)
        }
    }
}