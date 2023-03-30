package com.example.room.feature_node.presentation.notes

import com.example.room.feature_node.domain.model.Note
import com.example.room.feature_node.domain.util.NoteOrder
import com.example.room.feature_node.domain.util.OrderType


data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Desending),
    val isOrderSectionVisible: Boolean = false
)
