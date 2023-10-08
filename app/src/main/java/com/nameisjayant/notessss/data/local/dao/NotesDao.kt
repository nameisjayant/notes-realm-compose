package com.nameisjayant.notessss.data.local.dao

import com.nameisjayant.notessss.data.local.model.Note
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesDao @Inject constructor(
    private val realm: Realm
) {
    suspend fun insertNote(notes: Note) = realm.write {
        copyToRealm(notes, updatePolicy = UpdatePolicy.ALL)
    }

    // fetch all objects of a type as a flow, asynchronously
    fun getAllNotes(): Flow<ResultsChange<Note>> = realm.query<Note>().asFlow()

    suspend fun deleteNote(id: String) = realm.write {

        val findNote = query<Note>("id == $0", id).find()
        delete(findNote)
    }

    suspend fun updateNote(notes: Note?) = realm.write {

        val findNote = query<Note>("id == $0", notes?.id ?: "0").first().find()

        findNote?.apply {
            title = notes?.title ?: "-"
            description = notes?.description ?: "-"
        }
    }

}