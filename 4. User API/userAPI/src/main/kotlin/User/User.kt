package User

import org.json.JSONObject

data class User(
    var id : String?,
    var name : String?,
    var email :  String?,
    var age : Int?){
    constructor() : this(id = null, name = null, email = null, age = null)
    override fun toString(): String {
        val userJson = JSONObject()
        userJson.put("id",this.id)
        userJson.put("name", this.name)
        userJson.put("email", this.email)
        userJson.put("age", this.age)
        return userJson.toString()
    }

}