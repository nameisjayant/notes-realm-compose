package com.nameisjayant.notessss.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nameisjayant.notessss.features.notes.ui.screens.AddNotes
import com.nameisjayant.notessss.features.notes.ui.screens.NoteScreen


@Composable
fun NoteNavigation(
    navHostController: NavHostController
) {

    NavHost(navController = navHostController, startDestination = ScreenDestination.Note.route) {
        composable(ScreenDestination.Note.route) {
            NoteScreen(
                navHostController
            )
        }
        composable(ScreenDestination.AddNote.route) {
            AddNotes(navHostController)
        }
    }

}

sealed class ScreenDestination(val route: String) {

    data object Note : ScreenDestination("/note")
    data object AddNote : ScreenDestination("/addNote")

}