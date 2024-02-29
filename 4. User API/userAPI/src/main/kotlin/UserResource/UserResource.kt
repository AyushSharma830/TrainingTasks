package UserResource

import Constraints.Constraints
import User.User
import UserService.UserService
import com.google.gson.JsonObject
import org.json.JSONObject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

import utility.ResponseUtil

@Path("/users")
class UserResource {
    private val userService = UserService()

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllUsers() : String{
        return try{
            val users = userService.getAllUsers()
            ResponseUtil.success(data = users)
        }catch(e : IllegalArgumentException){
            ResponseUtil.error(status = 400, error = e.message)
        }
    }

    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUserById(@PathParam("id") id : String) : String {
        return try{
            val user = userService.getUserById(id)
            if (user != null) {
                ResponseUtil.success(data = user)
            } else {
                ResponseUtil.error(status = 400, error = Constraints.NO_USER_ERROR)
            }
        }catch(e : IllegalArgumentException){
            ResponseUtil.error(status = 400, error = e.message)
        }
    }

    @POST
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    fun addUser(userData : String?) : String {
        return try{
            if (userData.isNullOrBlank()) {
                ResponseUtil.error(status = 400, error = Constraints.BAD_REQ_ERROR)
            } else {
                val user = userService.addUser(userData)
                if (user != null) {
                    ResponseUtil.success(data = user)
                } else {
                    ResponseUtil.error(status = 500, error = Constraints.INT_SERVER_ERROR)
                }
            }
        }catch(e : IllegalArgumentException){
            ResponseUtil.error(status = 400, error = e.message)
        }
    }

    @PUT
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun partialUpdateUser(@PathParam("id") id : String, userData : String?) : String{
        return try{
            val existingUser = userService.getUserById(id)
            if(existingUser != null){
                if(userData == null){
                    ResponseUtil.error(status = 400, error = Constraints.BAD_REQ_ERROR)
                }else{
                    val updatedUser = userService.partialUpdateUser(existingUser, userData)
                    if(updatedUser != null){
                        ResponseUtil.success(data = updatedUser)
                    }else{
                        ResponseUtil.error(status = 500, error = Constraints.INT_SERVER_ERROR)
                    }
                }
            }else{
                ResponseUtil.error(status = 400, error = Constraints.NO_USER_ERROR)
            }
        }catch(e : IllegalArgumentException){
            ResponseUtil.error(status = 400, error = e.message)
        }
    }

    @DELETE
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun deleteUser(@PathParam("id") id : String) : String {
        return try{
            val deletedUser = userService.deleteUser(id)
            if (deletedUser != null) {
                ResponseUtil.success(data = deletedUser)
            } else {
                ResponseUtil.error(status = 400, error = Constraints.NO_USER_ERROR)
            }
        }catch(e : IllegalArgumentException){
            ResponseUtil.error(status = 400, error = e.message)
        }
    }
}