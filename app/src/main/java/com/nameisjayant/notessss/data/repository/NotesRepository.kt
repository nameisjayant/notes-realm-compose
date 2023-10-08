package com.nameisjayant.notessss.data.repository

import com.nameisjayant.notessss.data.local.dao.NotesDao
import com.nameisjayant.notessss.data.local.model.Note
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val notesDao: NotesDao
) {

    suspend fun insertNotes(notes: Note) = withContext(Dispatchers.IO) {
        notesDao.insertNote(notes)
    }

    fun getAllNotes(): Flow<ResultsChange<Note>> = notesDao.getAllNotes()

    suspend fun deleteNote(id: String) = withContext(Dispatchers.IO) {
        notesDao.deleteNote(id)
    }

    suspend fun updateNote(notes: Note) = withContext(Dispatchers.IO) {
        notesDao.updateNote(notes)
    }
}