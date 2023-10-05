package com.nameisjayant.notessss.data.local.dao

import com.nameisjayant.notessss.data.local.model.Notes
import io.realm.kotlin.Realm
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.find
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class NotesDao @Inject constructor(
    private val realm: Realm
) {
    suspend fun insertNote(notes: Notes) = realm.write {
        copyToRealm(notes)
    }

    fun getAllNotes() = realm.query<Notes>().find().toList()

    suspend fun deleteNote(id: ObjectId) = realm.write {
        val note = query<Notes>().find {
            it.find { item ->
                item.id == id
            }
        }
        note?.let {
            delete(note)
        }
    }

}