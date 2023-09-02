package com.example.room.feature_node.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.room.feature_node.domain.model.Note
import com.example.room.feature_node.domain.use_case.NoteUseCases
import com.example.room.feature_node.domain.util.NoteOrder
import com.example.room.feature_node.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotesViewModels @Inject constructor(
    private val notesUseCases : NoteUseCases
) : ViewModel()
{

    //Declaro una variable observable para los estados
    private val _state = mutableStateOf(NotesState())
    val state : State<NotesState> = _state

    /*  this is another way to declare it
          private var _state by  mutableStateOf(NotesState())
                private set
     */

    //una variable que me guarde el note recientemente eliminado
    private var recentlyDeletedNotes : Note? = null

    private var getNotesJob : Job? = null

    private val contador = mutableStateOf(0)

    //en la primera carga se hace ordenado por fecha y descendiente
    init {
        getNotes(NoteOrder.Date(OrderType.Desending))
    }


    /*************************************************************/

    fun onEvent( event: NotesEvent) {
            when(event){
                is NotesEvent.Order -> {
                    if(state.value.noteOrder::class == event.noteOrder::class &&
                       state.value.noteOrder.orderType == event.noteOrder.orderType){
                        return
                    }
                    getNotes(event.noteOrder)
                }

                is NotesEvent.DeleteNote -> {
                    viewModelScope.launch {
                       notesUseCases.deleteNote(event.note)
                       recentlyDeletedNotes = event.note
                    }
                }

                is NotesEvent.RestoreNote -> {
                    viewModelScope.launch {
                        notesUseCases.addNote(recentlyDeletedNotes ?: return@launch)
                        recentlyDeletedNotes = null
                    }

                }

                is NotesEvent.ToggleOrderSection ->{
                    _state.value = state.value.copy(
                        isOrderSectionVisible = !state.value.isOrderSectionVisible
                    )
                }

            }
        }

    private fun getNotes(noteOrder:NoteOrder){

        getNotesJob?.cancel()  //Dado que la recuperacion se hace por un flujo
                              // si estamos haciendo varias peticiones y hay alguna que no ha
                              //finalizado entonces cancel hace eso mismo e inicia un nuevo proceso de
                              //recuperacion de datos


        getNotesJob = notesUseCases.getNotes(noteOrder).onEach {
                           // con las notas regresadas se guardan y actualizan
                          //  en la variabla observables _state y state
                notes ->

                     contador.value = contador.value + 1
                           //Actualizamos los valores de la Data Clases
                           //  NoteState  que tiene: notes, orderType, isOrderSectionVisible

                          //funcion COPY se usa para las Data Classes
                    _state.value = state.value.copy(
                            notes = notes,
                            noteOrder = noteOrder)

                    println("Se imprimio $contador.value")



        }.launchIn(viewModelScope)


    }


}