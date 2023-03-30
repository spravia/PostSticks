package com.example.room.feature_node.presentation.notes

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.material.*

import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.room.feature_node.presentation.notes.components.NoteItem
import com.example.room.feature_node.presentation.notes.components.OrderSection
import com.example.room.feature_node.presentation.util.Screen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NotesViewModels = hiltViewModel()
) {
    val state: NotesState = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                      //navigate permite navegar a un destino componible
                    navController.navigate(Screen.AddEditNoteScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        },
        scaffoldState = scaffoldState
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = "Tus notas", style = MaterialTheme.typography.h4)

                IconButton(   //Si le dan click entonces llama a funcion Event para cambiar
                              //el valor que se està observando
                    onClick = { viewModel.onEvent(NotesEvent.ToggleOrderSection) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort"
                    )
                }
               }

            AnimatedVisibility(
                visible =  state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            )
            {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NotesEvent.Order(it))
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            //Pintamos la lista de notas creadas
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.notes) { 
                    NoteItem(note = it,
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .clickable {
                                     navController.navigate(
                                         Screen.AddEditNoteScreen.route +
                                                 "?noteId=${it.id}&noteColor=${it.color}"
                                     )
                                 } ,
                             onDeleteClick =
                             {                    //lanza una mensaje en la parte baja del Scaffold
                                               viewModel.onEvent(NotesEvent.DeleteNote(it))
                                               scope.launch {
                                               var result = scaffoldState.snackbarHostState.showSnackbar(
                                               message = "Seguro que desea eliminar?",
                                               actionLabel = "Deshacer"
                                                 )
                                              if (result == SnackbarResult.ActionPerformed) {
                                                 viewModel.onEvent(NotesEvent.RestoreNote)
                                              }
                                             }
                             }
                    )

                    //espacio entre cada nota
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


@Preview
@Composable
fun NoteScreenPreview() {

    val hil : NotesViewModels = hiltViewModel()

    NoteScreen( navController= rememberNavController(),
                viewModel = hil)
}


