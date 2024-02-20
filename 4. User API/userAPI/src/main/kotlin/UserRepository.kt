class UserRepository {
    private val users = mutableListOf<User>()

    fun getAllUsers() : List<User> {
        return users
    }

    fun getUserById(id : Int ) : User? {
        return users.firstOrNull{ it.id == id }
    }

    fun addUser(user : User ) : User? {
        users.add(user)
        return users.firstOrNull{ it.id == user.id }
    }

    fun updateUser(existingUser: User, newUser : User) : User? {
        val indexOfExistingUser = users.indexOf(existingUser)
        users[indexOfExistingUser] = newUser
        return users.firstOrNull{ it.id == newUser.id }
    }

    fun deleteUser(user : User) : User {
        users.remove(user)
        return user
    }
}