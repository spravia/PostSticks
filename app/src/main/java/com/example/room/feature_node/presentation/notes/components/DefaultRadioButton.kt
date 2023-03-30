package com.example.room.feature_node.presentation.notes.components


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DefaultRatioButton(
    text : String,
    selected: Boolean,
    modifier : Modifier = Modifier,
    onSelect : () -> Unit,
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        RadioButton(selected = selected ,
                    onClick = onSelect,
                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primary,
                                                        unselectedColor = MaterialTheme.colors.onBackground))
        Spacer(modifier = Modifier.width(8.dp))

        Text(text = text, style = MaterialTheme.typography.body1)
    }
}