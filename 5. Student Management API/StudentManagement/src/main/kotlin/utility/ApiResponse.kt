package utility

import com.google.gson.GsonBuilder

data class ApiResponse<T>(
    val status : Int?,
    val data : T? ,
    val error : String?
){
    constructor() : this(status = null, data = null, error = null)

    override fun toString(): String {
        return GsonBuilder().serializeNulls().create().toJson(this)
    }
}