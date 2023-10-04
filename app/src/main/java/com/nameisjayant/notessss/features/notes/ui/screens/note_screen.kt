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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nameisjayant.notessss.R
import com.nameisjayant.notessss.components.AppChipComponent
import com.nameisjayant.notessss.data.local.model.Notes
import com.nameisjayant.notessss.features.notes.ui.viewmodel.NotesEvent
import com.nameisjayant.notessss.features.notes.ui.viewmodel.NotesViewModel
import com.nameisjayant.notessss.features.notes.ui.viewmodel.RealmStates
import com.nameisjayant.notessss.utils.showToast
import kotlinx.coroutines.flow.collectLatest


@Composable
fun NoteScreen(
    viewModel: NotesViewModel = hiltViewModel()
) {

    var selected by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.notesssss), style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.W700,
                    fontSize = 24.sp
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
            Button(onClick = {
                viewModel.onEvent(
                    NotesEvent.AddNotesEvent(
                        Notes(
                            title = "Note Title",
                            description = "Note description"
                        )
                    )
                )
            }) {
                Text(text = "Add")
            }

        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.notesAddedEventFlow.collectLatest {
            when (it) {
                is RealmStates.Success -> {
                    context.showToast(context.getString(R.string.added))
                    viewModel.onEvent(NotesEvent.ShowNotes)
                }

                is RealmStates.Failure -> {
                    context.showToast(it.error)
                }

                RealmStates.Loading -> {

                }

            }
        }
    }

}

@Composable
fun SelectChips(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onValueChange: (Int) -> Unit
) {

    val chipList = listOf("Notes", "Highlights", "Favourites")
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .background(
                color = Color.LightGray.copy(alpha = 0.3f),
                shape = RoundedCornerShape(16.dp)
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