package com.nameisjayant.notessss.data.local.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.util.UUID

class Note() : RealmObject {

    @PrimaryKey
    var id: String = ""
    var title: String = ""
    var description: String = ""

    constructor(
        id: String = UUID.randomUUID().toString(),
        title: String,
        description: String
    ) : this() {
        this.title = title
        this.description = description
        this.id = id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }

    override fun toString(): String {
        return super.toString()
    }


}