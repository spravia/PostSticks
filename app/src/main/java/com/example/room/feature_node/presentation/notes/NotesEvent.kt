package com.example.room.feature_node.presentation.notes


import com.example.room.feature_node.domain.model.Note
import com.example.room.feature_node.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order (val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent ()
    object  RestoreNote : NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
