package com.nameisjayant.notessss.data.local.dao

import com.nameisjayant.notessss.data.local.model.Notes
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import javax.inject.Inject

class NotesDao @Inject constructor(
    private val realm: Realm
) {
    suspend fun insertNote(notes: Notes) = realm.write {
        copyToRealm(notes)
    }
    fun getAllNotes() = realm.query<Notes>().find().toList()
}