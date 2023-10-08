package com.nameisjayant.notessss.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nameisjayant.notessss.features.notes.ui.screens.AddNotes
import com.nameisjayant.notessss.features.notes.ui.screens.NoteScreen
import com.nameisjayant.notessss.features.notes.ui.viewmodel.NotesViewModel


@Composable
fun NoteNavigation(
    navHostController: NavHostController
) {
    val noteViewModel: NotesViewModel = viewModel()

    NavHost(navController = navHostController, startDestination = ScreenDestination.Note.route,
        enterTransition = {
            fadeIn(tween(0))
        }, exitTransition = {
            fadeOut(tween(0))
        }
    ) {
        composable(ScreenDestination.Note.route) {
            NoteScreen(
                navHostController,
                viewModel = noteViewModel
            )
        }
        composable(ScreenDestination.AddNote.route) {
            AddNotes(navHostController, viewModel = noteViewModel)
        }
    }

}

sealed class ScreenDestination(val route: String) {

    data object Note : ScreenDestination("/note")
    data object AddNote : ScreenDestination("/addNote")

}