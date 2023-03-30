package com.example.room.di

import android.app.Application
import androidx.room.Room
import com.example.room.feature_node.data.data_source.NoteDataBase
import com.example.room.feature_node.data.repository.NoteRepositoryImpl
import com.example.room.feature_node.domain.repository.NoteRepository
import com.example.room.feature_node.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton

import dagger.hilt.components.SingletonComponent

@Module   //modulo para proveer dependencia
@InstallIn(SingletonComponent::class)    //Esta anotaciòn define el alcance, esto quiere decir cuanto tiempo va a vivir esa instancia creada
object AppModule {


    @Provides
    @Singleton    //Que use el patron de diseño Singleton y solo permite crear una unica instancia
                  //esto construye la BD
    fun provideNoteDatabase(app:Application): NoteDataBase {
        return Room.databaseBuilder(
            app, NoteDataBase::class.java,
            NoteDataBase.DATABASE_NAME
        ).build()
    }


    @Provides
    @Singleton   //Que use el patron de diseño Singleton y solo permite crear una unica instancia
                 //Instancia la clase que tienen acceso a la BD
                 //  getNotes, getNoteById, insertNote, deleteNote
    fun provideNoteRepository(dao: NoteDataBase) : NoteRepository
    {
         //Se devuelve la implementaciòn de la interfaz NoteRepository especificamente el DAO
        return NoteRepositoryImpl(dao.noteDao)

    }


    @Provides
    @Singleton    //Que use el patron de diseño Singleton y solo permite crear una unica instancia
                  //devuelve mediante inyeccion de dependencia los Casus de uso necesarios
                  // a cada Caso de Uso de le pasa la referencia del DAO a traves de repository
    fun provideNoteUseCases(repository:NoteRepository) : NoteUseCases {
        return  NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository = repository)
        )
    }

    @Provides
    @Singleton
    fun pruebaDeSavas() : String{
        return "Test"
    }
}