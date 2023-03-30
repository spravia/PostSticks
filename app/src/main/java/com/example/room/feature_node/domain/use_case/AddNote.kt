package com.example.room.feature_node.domain.use_case

import com.example.room.feature_node.domain.model.InvalidNoteException
import com.example.room.feature_node.domain.model.Note
import com.example.room.feature_node.domain.repository.NoteRepository


class AddNote (private val repository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note : Note){
        if (note.title.isBlank()){
            throw InvalidNoteException("El titulo es invalido")
        }
        if (note.content.isBlank()){
            throw InvalidNoteException("El contenido es invalido")
        }
        repository.insertNote(note)
    }
}