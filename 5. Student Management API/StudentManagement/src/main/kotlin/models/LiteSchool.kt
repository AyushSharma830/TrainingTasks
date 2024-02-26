package models

import com.google.gson.GsonBuilder

data class LiteSchool(
var schoolId : String?,
var schoolName : String?
) {
constructor() : this(schoolId = null, schoolName = null)

override fun toString(): String {
    return GsonBuilder().serializeNulls().create().toJson(this)
}
}