package com.nameisjayant.notessss.features.notes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nameisjayant.notessss.R
import com.nameisjayant.notessss.components.AppChipComponent
import com.nameisjayant.notessss.components.NoteEachRow
import com.nameisjayant.notessss.features.notes.ui.viewmodel.NotesEvent
import com.nameisjayant.notessss.features.notes.ui.viewmodel.NotesViewModel
import com.nameisjayant.notessss.features.notes.ui.viewmodel.RealmStates
import com.nameisjayant.notessss.navigation.ScreenDestination
import com.nameisjayant.notessss.utils.showToast
import kotlinx.coroutines.flow.collectLatest


@Composable
fun NoteScreen(
    navHostController: NavHostController, viewModel: NotesViewModel
) {

    var selected by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val notes by viewModel.notesEventFlow.collectAsState()

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.notesssss), style = TextStyle(
                    color = Color.Black, fontWeight = FontWeight.W700, fontSize = 24.sp
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(),
                color = Color.LightGray.copy(alpha = 0.2f)
            )
            Spacer(modifier = Modifier.height(20.dp))
            SelectChips(selectedIndex = selected, onValueChange = {
                selected = it
            })
            Spacer(modifier = Modifier.height(20.dp))
            when (selected) {
                0 -> {
                    if (notes.data.isNotEmpty()) LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(notes.data, key = { it.id }) {
                            NoteEachRow(note = it, editNote = {
                                viewModel.setNote(it)
                                navHostController.navigate(ScreenDestination.AddNote.route)
                            }) { deleteId ->
                                viewModel.onEvent(NotesEvent.DeleteNoteEvent(deleteId))
                            }
                        }
                    }
                }
            }

        }

        FloatingActionButton(
            onClick = {
                navHostController.navigate(ScreenDestination.AddNote.route)
            },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.BottomEnd),
            containerColor = Color(0XFFE4FFE6)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.deleteNoteEventFlow.collectLatest {
            when (it) {
                is RealmStates.Success -> {
                    viewModel.onEvent(NotesEvent.ShowNotesEvent)
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
        viewModel.onEvent(NotesEvent.ShowNotesEvent)
    }
}

@Composable
fun SelectChips(
    modifier: Modifier = Modifier, selectedIndex: Int, onValueChange: (Int) -> Unit
) {

    val chipList = listOf("Notes", "Highlights", "Favourites")
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .background(
                color = Color.LightGray.copy(alpha = 0.3f), shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 2.dp)
                .fillMaxWidth()
                .horizontalScroll(scrollState)
        ) {
            chipList.forEachIndexed { index, title ->
                AppChipComponent(
                    text = title,
                    index = index,
                    selected = index == selectedIndex,
                    onValueChange = onValueChange
                )
            }
        }
    }

}