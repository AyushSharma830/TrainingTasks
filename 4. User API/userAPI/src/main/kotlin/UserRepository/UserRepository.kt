package UserRepository

import User.User

class UserRepository {

    companion object{
        val users = mutableListOf<User>()
    }

    fun validateEmailUniqueness(email : String, existingUserId : String?) : Boolean{
        return users.any{ it.email == email && it.id != existingUserId}
    }

    fun getAllUsers() : List<User> {
        return try{
            users
        }catch(e : IllegalArgumentException){
            throw IllegalArgumentException(e.message)
        }
    }

    fun getUserById(id : String ) : User? {
        return try{
            users.firstOrNull{ it.id == id }
        }catch(e : IllegalArgumentException){
            throw IllegalArgumentException(e.message)
        }
    }

    fun addUser(user : User) : User? {
        return try{
            users.add(user)
            users.firstOrNull { it.id == user.id }
        }catch(e : IllegalArgumentException){
            throw IllegalArgumentException(e.message)
        }
    }

    fun partialUpdateUser(existingUser: User, newUser : User) : User? {
        return try{
            val indexOfExistingUser = users.indexOf(existingUser)
            users[indexOfExistingUser] = newUser
            users.firstOrNull { it.id == newUser.id }
        }catch(e: IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun deleteUser(id : String) : User? {
        return try{
            val iterator = users.iterator()
            while (iterator.hasNext()) {
                val user = iterator.next()
                if (user.id == id) {
                    iterator.remove()
                    return user
                }
            }
            return null
        }catch(e : IllegalArgumentException){
            throw IllegalArgumentException(e.message)
        }
    }
}