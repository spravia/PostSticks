package com.example.room.feature_node.presentation.util


  //Aquì defino mis pantallas

sealed class Screen(val route : String) {

    object NoteScreen : Screen("notes_screen")
    object AddEditNoteScreen : Screen("add_edit_note_screen")


}
