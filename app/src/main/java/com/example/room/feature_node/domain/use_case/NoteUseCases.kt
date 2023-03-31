package com.example.room.feature_node.domain.use_case

import com.example.room.feature_node.presentation.add_edit_note.AddEditNoteEvent
import javax.inject.Inject


   //inyectamos depencencia a la data clase las clases de Caso de Uso
data class NoteUseCases @Inject constructor(
    val getNotes : GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote: GetNote )

