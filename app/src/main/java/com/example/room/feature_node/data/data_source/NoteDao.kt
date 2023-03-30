package com.example.room.feature_node.data.data_source

import androidx.room.*
import com.example.room.feature_node.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("Select * FROM note")
    fun getNotes() : Flow<List<Note>>

    @Query("Select * FROM note WHERE id= :id ")
    suspend fun getNoteById(id: Int): Note?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}