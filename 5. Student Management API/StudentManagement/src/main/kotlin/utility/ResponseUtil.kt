package utility

object ResponseUtil {
    fun <T> success(data : T?) : String{
        return ApiResponse(200, data, null).toString()
    }

    fun error(status: Int, error: String?) : String{
        return ApiResponse(status, null, error).toString()
    }
}