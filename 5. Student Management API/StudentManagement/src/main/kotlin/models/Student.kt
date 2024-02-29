package models

import com.google.gson.GsonBuilder
import org.json.JSONObject

data class Student(
    var id : String?,
    var name : String?,
    var email : String?,
    var rollNo : String?,
    var schoolInfo : LiteSchool?,
    var createdAt : Long?
){
    constructor() : this(id = null, name = null, email = null, rollNo = null, schoolInfo = null, createdAt = null)

    override fun toString(): String {
// Using GsonBuilder as .put method was giving error while mapping student
        return GsonBuilder().serializeNulls().create().toJson(this)
    }
}