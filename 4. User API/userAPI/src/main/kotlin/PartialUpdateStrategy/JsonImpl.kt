package PartialUpdateStrategy

import User.User
import com.google.gson.Gson
import org.json.JSONObject

class JsonImpl : PartialUpdatable {
    override fun partialUpdate(existingUser: User, userJson: JSONObject): User {
        return try{
            var updatedUser = existingUser.copy()
            val key = userJson["key"].toString()
            val value = userJson["value"]
            val updatedUserJson = JSONObject(updatedUser.toString())
            updatedUserJson.put(key, value)
            updatedUser = Gson().fromJson(updatedUserJson.toString(),User::class.java)
            updatedUser
        }catch(e : IllegalArgumentException){
            throw IllegalArgumentException(e.message)
        }
    }
}