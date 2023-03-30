package com.example.room.feature_node.data.repository

import com.example.room.feature_node.data.data_source.NoteDao
import com.example.room.feature_node.domain.model.Note
import com.example.room.feature_node.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow



            //Implemente la interfaz NoteRepository
class NoteRepositoryImpl(private val dao : NoteDao) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        return dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return dao.deleteNote(note)
    }

}