package com.example.room.feature_node.presentation.notes.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.room.feature_node.domain.model.Note

@Composable
fun NoteItem(
    note : Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize : Dp = 30.dp,
    onDeleteClick : () -> Unit
){
    Box(modifier = modifier)
    {

        Canvas(modifier = Modifier.matchParentSize())
           {          //toPx convierte los dp a Pixeles
                val clipPath = Path().apply {
                    lineTo(size.width - cutCornerSize.toPx(),0f)
                    lineTo(size.width, cutCornerSize.toPx())
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }


               clipPath(clipPath){
                    drawRoundRect(
                        color = Color(note.color),
                        size = size,
                        cornerRadius = CornerRadius(cornerRadius.toPx())
                    )

                   drawRoundRect(
                       color = Color( ColorUtils.blendARGB(note.color, 0x000000, 0.2f)),
                       topLeft = Offset(size.width - cutCornerSize.toPx(),- 100f),
                       size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                       cornerRadius = CornerRadius(cornerRadius.toPx())
                       )


               }
           }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(end = 32.dp))
        {
            
            Text(text = note.title,
                 style = MaterialTheme.typography.h6,
                 color = MaterialTheme.colors.onSurface,
                 maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = note.content,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }


        IconButton(onClick = onDeleteClick, modifier= Modifier.align(Alignment.BottomEnd))
        {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Note", tint = MaterialTheme.colors.onSurface)
        }
        
        
    }
}


@Preview()
@Composable
fun NoteItemPreview(){

    val currentTimestamp = System.currentTimeMillis()
    val redOrange = Color(0xffffab91).toArgb()
    val note1 = Note("Test","Valor", timestamp = currentTimestamp, color = redOrange )


    NoteItem( note = note1 ,
              modifier = Modifier
                  .fillMaxWidth()
                  .clickable {} ,
              cornerRadius = 10.dp,
              cutCornerSize = 30.dp,
              onDeleteClick = {})
}

