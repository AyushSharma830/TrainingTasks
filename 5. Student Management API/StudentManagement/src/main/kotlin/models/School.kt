package models

import com.google.gson.GsonBuilder

data class School(
    var id : String?,
    var name : String?,
    var location : String?,
    var createdAt : Long?
) {
    constructor() : this(id = null, name = null, location = null, createdAt = null)

    override fun toString(): String {
        return GsonBuilder().serializeNulls().create().toJson(this)
    }
}