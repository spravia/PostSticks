package com.example.room.feature_node.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import com.example.room.feature_node.domain.util.NoteOrder
import com.example.room.feature_node.domain.util.OrderType
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Desending),
    onOrderChange : (NoteOrder) -> Unit
){

    Column(modifier = modifier) {

        Row(modifier = Modifier.fillMaxWidth()){

            DefaultRatioButton(text = "Titulo" ,
                               selected = noteOrder is NoteOrder.Tittle,
                               onSelect = { onOrderChange(NoteOrder.Tittle(noteOrder.orderType))}
                               )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRatioButton(text = "Hora" ,
                selected = noteOrder is NoteOrder.Date,
                onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType))}
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRatioButton(text = "Color" ,
                selected = noteOrder is NoteOrder.Color,
                onSelect = { onOrderChange(NoteOrder.Color(noteOrder.orderType))}
            )
            
        }

        Spacer(modifier = Modifier.height(3.dp))
        
        Row (
            modifier = Modifier.fillMaxWidth()
             )
        {

            DefaultRatioButton(text = "Ascendiente" ,
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(noteOrder.copy(OrderType.Ascending))}
            )
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRatioButton(text = "Descendiente" ,
                selected = noteOrder.orderType is OrderType.Desending,
                onSelect = { onOrderChange(noteOrder.copy(OrderType.Desending))}
            )
        }
    }
}