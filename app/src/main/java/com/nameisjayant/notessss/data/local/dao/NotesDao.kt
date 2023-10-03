package com.nameisjayant.notessss.data.local.dao

import com.nameisjayant.notessss.data.local.model.Notes
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class NotesDao {

    private val config = RealmConfiguration.create(setOf(Notes::class))
    private val realm = Realm.open(config)

    fun insert(notes: Notes) = realm.writeBlocking {
        copyToRealm(notes)
    }


}