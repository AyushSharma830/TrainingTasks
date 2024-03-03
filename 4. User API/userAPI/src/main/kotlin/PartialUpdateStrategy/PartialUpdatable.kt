package PartialUpdateStrategy

import User.User
import org.json.JSONObject

interface PartialUpdatable{
    fun partialUpdate(existingUser: User, userJson : JSONObject) : User
}