package uy.com.temperoni.recipes.ui.compose.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import uy.com.temperoni.recipes.ui.model.Instruction


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
                .background(color = MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                text = "${index + 1}",
                style = MaterialTheme.typography.titleSmall,
            )
        }
        Text(
            color = MaterialTheme.colorScheme.onSurface,
            text = text,
            modifier = Modifier.padding(12.dp, 0.dp, 0.dp, 0.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun Steps(instructions: List<Instruction>) {
    instructions.forEach { instructionItem ->
        if (instructionItem.description.isNotBlank()) {
            SubTitle(instructionItem.description)
        }
        instructionItem.steps.forEachIndexed { index, step ->
            Step(step, index)
        }
    }
}