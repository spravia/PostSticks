package com.example.room.feature_node.domain.use_case

import com.example.room.feature_node.domain.model.Note
import com.example.room.feature_node.domain.repository.NoteRepository
import com.example.room.feature_node.domain.util.NoteOrder
import com.example.room.feature_node.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(private val repository: NoteRepository) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Desending)): Flow<List<Note>>
    {

        return repository.getNotes().map { notes ->

        when (noteOrder.orderType){

            is OrderType.Ascending -> {
               when (noteOrder) {
                   is NoteOrder.Tittle -> notes.sortedBy { it.title.lowercase() }
                   is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                   is NoteOrder.Color -> notes.sortedBy { it.color }
               }
            }

            is OrderType.Desending -> {
                when (noteOrder) {
                    is NoteOrder.Tittle -> notes.sortedByDescending { it.title.lowercase() }
                    is NoteOrder.Date -> notes.sortedByDescending {  it.timestamp  }
                    is NoteOrder.Color -> notes.sortedByDescending {  it.color  }
                }
            }
        }
        }
    }
}