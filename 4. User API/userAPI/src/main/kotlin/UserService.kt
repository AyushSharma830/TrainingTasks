import org.json.JSONObject
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

class UserService {
    private val userRepository = UserRepository()

    fun getAllUsers() : List<String>? {
        val users : List<User>? = userRepository.getAllUsers()
        if(users != null){
//            val addedUsers: List<String>? = GsonBuilder().serializeNulls().create().toJson(users,List<User>::class.java)
            val addedUsers : List<String> = users.map { Gson().toJson(it).toString() }
            return addedUsers
        }
        return null
    }

    fun getUserById(id : Int?) : String?{
        val user : User? = userRepository.getUserById(id)
        if(user != null){
            val addedUserJson = GsonBuilder().serializeNulls().create().toJson(user)
            return addedUserJson.toString()
        }
        return null
    }

    fun addUser(data : String?) : Response{
        if(data != null){
            val userJson : JSONObject? = JSONObject(data)
            val user : User? = Gson().fromJson(userJson.toString(),User::class.java)
            userRepository.addUser(user)
            return Response.ok("New User Added").type(MediaType.APPLICATION_JSON).build()
        }
        return Response.ok("NULL Data").type(MediaType.APPLICATION_JSON).build()
    }
}