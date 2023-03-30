package com.example.room.feature_node.presentation.add_edit_note


import android.annotation.SuppressLint

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.room.feature_node.domain.model.Note
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel : AddEditNoteViewModel = hiltViewModel()
){
    val tittleState = viewModel.noteTittle.value
    val contentState = viewModel.noteContent.value

    val scaffoldState = rememberScaffoldState()

    val noteBackGroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true)
    {
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }

                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )

                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton( onClick = { viewModel.onEvent(AddEditNoteEvent.SaveNote) } ,
            backgroundColor = MaterialTheme.colors.primary
               ) {
                Icon(imageVector = Icons.Default.Save, contentDescription ="Save note" )
            }
        },
        scaffoldState = scaffoldState
    )
    {

         //Digujar los circulos con los colores

            Column(modifier = Modifier
                .fillMaxSize()
                .background(noteBackGroundAnimatable.value)
                .padding(16.dp)) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween)
                {
                    Note.noteColors.forEach { color ->
                        val colorInt = color.toArgb()
                        Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .shadow(15.dp, CircleShape)
                                    .clip(CircleShape)
                                    .background(color)
                                    .border(
                                        width = 3.dp,
                                        color = if (viewModel.noteColor.value == colorInt) {
                                            Color.Black
                                        } else Color.Transparent,
                                        shape = CircleShape
                                    )    //Si le doy clik le doy el color y lo hago animado
                                    .clickable {
                                        scope.launch {
                                            noteBackGroundAnimatable.animateTo(
                                                targetValue = Color(colorInt),
                                                animationSpec = tween(
                                                    durationMillis = 500
                                                )
                                            )
                                        }
                                        viewModel.onEvent(AddEditNoteEvent.ChageColor(colorInt))
                                    }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TransparentHintTextField(text = tittleState.text,
                                         hint = tittleState.hint,
                                         onValueChange = {
                                                 viewModel.onEvent(AddEditNoteEvent.EnteredTittle(it))
                                         },
                                         onFocusChange = {
                                             viewModel.onEvent(AddEditNoteEvent.ChangeTittleFocus(it))
                                         },
                                         isHistVisible = tittleState.isHintVisible,
                                         singleLine = true,
                                         textStyle = MaterialTheme.typography.h5
                 )

                Spacer(modifier = Modifier.height(16.dp))

                TransparentHintTextField(text = contentState.text,
                    hint = contentState.hint,
                    onValueChange = {
                        viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                    },
                    isHistVisible = contentState.isHintVisible,
                    textStyle = MaterialTheme.typography.body1,
                    modifier = Modifier.fillMaxHeight()
                )

            }
    }
}

