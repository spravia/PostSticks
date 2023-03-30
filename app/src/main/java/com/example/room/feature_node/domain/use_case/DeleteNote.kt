package com.example.room.feature_node.domain.use_case

import android.provider.ContactsContract
import com.example.room.feature_node.domain.model.Note
import com.example.room.feature_node.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
)

{
    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}