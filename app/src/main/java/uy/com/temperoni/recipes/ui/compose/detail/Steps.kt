package uy.com.temperoni.recipes.ui.compose.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uy.com.temperoni.recipes.dto.InstructionItem


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
                style = MaterialTheme.typography.subtitle2,
            )
        }
        Text(
            color = MaterialTheme.colors.onSurface,
            text = text,
            modifier = Modifier.padding(12.dp, 0.dp, 0.dp, 0.dp),
            style = MaterialTheme.typography.body1
        )
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