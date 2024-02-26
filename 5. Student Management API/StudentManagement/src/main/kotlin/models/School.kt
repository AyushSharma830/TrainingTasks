package models

import org.json.JSONObject

data class School(
    var id : String?,
    var name : String?,
    var location : String?,
    var createdAt : Long?
) {
    constructor() : this(id = null, name = null, location = null, createdAt = null)

    override fun toString(): String {
        val schoolJson = JSONObject()
        schoolJson.put("id", this.id)
        schoolJson.put("name", this.name)
        schoolJson.put("location", this.location)
        schoolJson.put("createdAt", this.createdAt)
        return schoolJson.toString()
    }
}