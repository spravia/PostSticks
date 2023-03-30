package com.example.room.feature_node.presentation.add_edit_note

import androidx.lifecycle.ViewModel
import com.example.room.feature_node.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.room.feature_node.domain.model.InvalidNoteException
import com.example.room.feature_node.domain.model.Note
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private  val notesUsesCases : NoteUseCases,
    savedStateHandle: SavedStateHandle
):ViewModel(){

    private val _noteTittle = mutableStateOf(NoteTextFieldState(hint = "Enter title"))
    val noteTittle : State<NoteTextFieldState> = _noteTittle

    private val _noteContent = mutableStateOf(NoteTextFieldState(hint = "Enter some content"))
    val noteContent : State<NoteTextFieldState> = _noteContent

    private var _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor : State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    var eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId : Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let {
         noteId ->
            if(noteId != -1){
                viewModelScope.launch {
                    notesUsesCases.getNote(noteId)?.also{
                     note ->
                          currentNoteId = note.id
                          _noteTittle.value = noteTittle.value.copy(text = note.title, isHintVisible = false)
                          _noteContent.value = noteContent.value.copy(text = note.content, isHintVisible = false)
                          _noteColor.value = noteColor.value
                    }
                }
            }
        }
    }

    /*************************************************************/

    fun onEvent(event: AddEditNoteEvent){
        when(event){
            is AddEditNoteEvent.EnteredTittle -> {
                _noteTittle.value = noteTittle.value.copy(text = event.value)
            }
            is AddEditNoteEvent.ChangeTittleFocus -> {
                _noteTittle.value = noteTittle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteTittle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(text = event.value)
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteContent.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChageColor -> {
                // TODO esto es temporal
                //_noteColor = event.color

                _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try{
                        notesUsesCases.addNote(
                            Note(title = noteTittle.value.text, content = noteContent.value.text, timestamp = System.currentTimeMillis(),
                            color = noteColor.value, id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    }catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldnt save note"
                            )
                        )
                    }
                }

            }
        }

    }


    sealed class UiEvent {
        data class ShowSnackbar(val message : String) : UiEvent()
        object SaveNote : UiEvent()
    }
}