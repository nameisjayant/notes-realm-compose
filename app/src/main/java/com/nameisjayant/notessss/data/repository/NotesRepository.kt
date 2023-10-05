package com.nameisjayant.notessss.data.repository

import com.nameisjayant.notessss.data.local.dao.NotesDao
import com.nameisjayant.notessss.data.local.model.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val notesDao: NotesDao
) {

    suspend fun insertNotes(notes: Notes) = withContext(Dispatchers.IO) {
        notesDao.insertNote(notes)
    }

    fun getAllNotes() = notesDao.getAllNotes()

    suspend fun deleteNote(id:ObjectId) = withContext(Dispatchers.IO){
        notesDao.deleteNote(id)
    }
}