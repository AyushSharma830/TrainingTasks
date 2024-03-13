package UserService

import PartialUpdateStrategy.JsonImpl
import PartialUpdateStrategy.PartialUpdatable
import User.User
import UserRepository.UserRepository
import ValidationService.ValidationService
import com.google.gson.Gson
import org.json.JSONObject
import java.util.UUID

class UserService {
    private val userRepository = UserRepository()
    private val validationService = ValidationService()
//    private val partialUpdatable: PartialUpdateStrategy = SwitchCaseStrategy()
    private val partialUpdatable : PartialUpdatable = JsonImpl()

    fun getAllUsers() : List<User> {
        return try{
            val users = userRepository.getAllUsers()
            users
        }catch(e : IllegalArgumentException){
            throw IllegalArgumentException(e.message)
        }
    }

    fun getUserById(id : String) : User?{
        return try{
            userRepository.getUserById(id)
        }catch(e : IllegalArgumentException){
            throw IllegalArgumentException(e.message)
        }
    }

    fun addUser(userData : String) : User? {
        return try{
            val user = Gson().fromJson(userData, User::class.java)
            user.id = UUID.randomUUID().toString()
            validationService.validateFields(user,null)
            val addedUser = userRepository.addUser(user)
            addedUser
        }catch(e : IllegalArgumentException){
            throw IllegalArgumentException(e.message)
        }
    }

    fun partialUpdateUser(existingUser: User, userData: String ) : User? {
        return try{
            val userJson = JSONObject(userData)
            val newUser = partialUpdatable.partialUpdate(existingUser, userJson)
            validationService.validateFields(newUser, existingUser.id)
            val updatedUser = userRepository.partialUpdateUser(existingUser, newUser)
            updatedUser
        }catch(e : IllegalArgumentException){
            throw IllegalArgumentException(e.message)
        }
    }

    fun deleteUser(id : String) : User? {
        return try{
            userRepository.deleteUser(id)
        }catch(e : IllegalArgumentException){
            throw IllegalArgumentException(e.message)
        }
    }
}