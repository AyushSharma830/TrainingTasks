package utility

object ResponseUtil {
    fun <T> toResponse(status : Int, data : T?, error : String?) : ApiResponse<T>{
        return ApiResponse(status, data, error)
    }
}