package com.nameisjayant.notessss.data.local.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Notes() : RealmObject {

    @PrimaryKey
    var id: ObjectId = ObjectId()
    var title: String = ""
    var description: String = ""

    constructor(
        title: String,
        description: String
    ) : this() {
        this.title = title
        this.description = description
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Notes

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