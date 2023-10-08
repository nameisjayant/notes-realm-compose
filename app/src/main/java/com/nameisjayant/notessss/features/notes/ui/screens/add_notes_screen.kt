package com.nameisjayant.notessss.features.notes.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nameisjayant.notessss.R
import com.nameisjayant.notessss.components.TextfieldComponent
import com.nameisjayant.notessss.data.local.model.Note
import com.nameisjayant.notessss.features.notes.ui.viewmodel.NotesEvent
import com.nameisjayant.notessss.features.notes.ui.viewmodel.NotesViewModel
import com.nameisjayant.notessss.features.notes.ui.viewmodel.RealmStates
import com.nameisjayant.notessss.utils.showToast
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AddNotes(
    navHostController: NavHostController, viewModel: NotesViewModel
) {
    val currentNote by viewModel.editNote.collectAsState()
    var title by remember {
        mutableStateOf(
            if (currentNote == null) "" else currentNote?.title ?: ""
        )
    }
    var description by remember {
        mutableStateOf(
            if (currentNote == null) "" else currentNote?.description ?: ""
        )
    }
    val context = LocalContext.current


    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    navHostController.navigateUp()
                    viewModel.setNote(null)
                }, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
                Text(
                    text = if (currentNote == null) stringResource(R.string.add_note) else stringResource(
                        R.string.edit_note
                    ), style = TextStyle(
                        color = Color.Black, fontWeight = FontWeight.W600, fontSize = 20.sp
                    )
                )
                if (title.isNotEmpty() && description.isNotEmpty()) IconButton(onClick = {
                    if (currentNote == null)
                        viewModel.onEvent(
                            NotesEvent.AddNotesEvent(
                                Note(
                                    title = title, description = description
                                )
                            )
                        )
                    else viewModel.onEvent(
                        NotesEvent.UpdateNoteEvent(
                            Note(
                                title = title, description = description,
                                id = currentNote?.id ?: ""
                            )
                        )
                    )
                }, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
                else Box {}
            }
            Spacer(modifier = Modifier.height(20.dp))
            Divider(
                modifier = Modifier.fillMaxWidth(), color = Color.LightGray.copy(alpha = 0.3f)
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextfieldComponent(
                value = title, placeholder = stringResource(id = R.string.title), style = TextStyle(
                    color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.W600
                ), onValueChange = {
                    title = it
                }, maxLine = 1, keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            TextfieldComponent(value = description,
                placeholder = stringResource(id = R.string.add_note_),
                style = TextStyle(
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                ),
                onValueChange = {
                    description = it
                })

        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.updateNoteEventFlow.collectLatest {
            when (it) {
                is RealmStates.Success -> {
                    viewModel.setNote(null)
                    context.showToast(context.getString(R.string.note_updated))
                    navHostController.navigateUp()
                }

                is RealmStates.Failure -> {
                    context.showToast(it.error)
                }

                RealmStates.Loading -> {

                }

            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.notesAddedEventFlow.collectLatest {
            when (it) {
                is RealmStates.Success -> {
                    viewModel.setNote(null)
                    context.showToast(context.getString(R.string.added))
                    navHostController.navigateUp()
                }

                is RealmStates.Failure -> {
                    context.showToast(it.error)
                }

                RealmStates.Loading -> {

                }

            }
        }
    }
    BackHandler {
        navHostController.navigateUp()
        viewModel.setNote(null)
    }

}