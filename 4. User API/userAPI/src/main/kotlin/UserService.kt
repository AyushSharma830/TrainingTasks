import org.json.JSONObject
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

class UserService {
    private val userRepository = UserRepository()

    fun getAllUsers() : List<User> {
        val users  = userRepository.getAllUsers()
        return users
    }

    fun getUserById(id : Int) : User?{
        return userRepository.getUserById(id)
    }

    fun addUser(userData : String) : User? {
        val user  = Gson().fromJson(userData,User::class.java)
        val addedUser  = userRepository.addUser(user)
        return addedUser
    }

    fun updateUser(existingUser: User, userData : String) : User? {
        val newUser = Gson().fromJson(userData,User::class.java)
        val updatedUser = userRepository.updateUser(existingUser,newUser)
        return updatedUser
    }

    fun deleteUser(user : User) : User {
        return userRepository.deleteUser(user)
    }
}