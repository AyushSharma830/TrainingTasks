package PartialUpdateStrategy

import User.User
import org.json.JSONObject

class SwitchCaseImpl : PartialUpdatable {
    override fun partialUpdate(existingUser: User, userJson: JSONObject): User {
        return try{
            val updatedUser = existingUser.copy()
            val key = userJson["key"].toString()
            val value = userJson["value"].toString()
            when (key) {
                "name" -> updatedUser.name = value
                "email" -> updatedUser.email = value
                "age" -> updatedUser.age = value.toInt()
                else -> {
                    throw IllegalArgumentException("No Valid Key")
                }
            }
            updatedUser
        }catch (e : IllegalArgumentException){
            throw IllegalArgumentException(e.message)
        }
    }
}