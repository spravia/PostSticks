package com.example.room.feature_node.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.room.feature_node.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false  //se agregó
)

 abstract class NoteDataBase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}