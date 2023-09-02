
//https://www.youtube.com/watch?v=8YPXv7xKh2w&t=1550s
// By Phillpp Lackner

// https://www.youtube.com/watch?v=t6ZuzSu2UHI
//https://www.youtube.com/watch?v=7FptmAjBdsA


package com.example.room.feature_node.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.room.ui.theme.RoomTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.room.feature_node.presentation.add_edit_note.AddEditNoteScreen
import com.example.room.feature_node.presentation.notes.NoteScreen
import com.example.room.feature_node.presentation.notes.NotesViewModels
import com.example.room.feature_node.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomTheme (
              //  darkTheme = true
            )
             {
                Surface(
                    color = MaterialTheme.colors.surface
                 )
                  {

                    val navController = rememberNavController()


                    //Instancio la clase viewModel
                    val viewModelState = viewModel<NotesViewModels>()

                    //val h = hiltViewModel<NotesViewModels>()

                    val exampleViewModel: NotesViewModels by viewModels()

                    //Sirve para construir un componente que permite la navegación
                    // a treves de elementos componibles de la app
                    // aqui se indica cual sera el elemento componible de inicio
                    NavHost(navController = navController, startDestination = Screen.NoteScreen.route){

                        //Definimos nuestras pantallas con composable, les damos>
                        // un nombre en route o mediante una sealed class
                        // agregamos parametros si se require
                        // y en en el Content agregamos la función que Componible
                        // que deseamos llamar

                        composable(route = Screen.NoteScreen.route)
                        {

                            val vm = viewModel<NotesViewModels>(factory = defaultViewModelProviderFactory)

                            NoteScreen(
                                navController = navController,
                                //viewModel = viewModelState
                            )
                        }

                        composable(route = Screen.AddEditNoteScreen.route +
                                "?noteId={noteId}&noteColor={noteColor}",

                            arguments = listOf(
                                navArgument(name = "noteId")
                                {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(name = "noteColor")
                                {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        )
                        {

                            val color = it.arguments?.getInt("noteColor") ?: -1

                            AddEditNoteScreen(navController = navController, noteColor = color)

                        }
                    }
                }     // fin surface
            }
        }
    }
}


