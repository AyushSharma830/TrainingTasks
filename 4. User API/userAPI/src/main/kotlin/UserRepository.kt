class UserRepository {
    private val users : MutableList<User> = mutableListOf<User>()

    fun getAllUsers() : List<User>? {
        return users
    }

    fun getUserById(id : Int? ) : User? {
        val user : User? = users.find{ it.id == id }
        return user
    }

    fun addUser(user : User? ) {
        if(user != null)    users.add(user)
    }
}