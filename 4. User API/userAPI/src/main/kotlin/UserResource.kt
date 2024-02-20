import javax.ws.rs.*
import javax.ws.rs.core.MediaType

import utility.ResponseUtil

@Path("/users")
class UserResource {
    private val userService = UserService()

    @GET                    //READ All Users
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllUsers() : String{
        val users : List<User> = userService.getAllUsers()
        return ResponseUtil.toResponse(status = 200, data = users, error = null).toString()
    }

    @GET                    //READ A Particular User
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUserById(@PathParam("id") id : Int) : String {
        val user : User? = userService.getUserById(id)
        return if(user == null)    {
            ResponseUtil.toResponse(status = 400, data = null, error ="Bad Request. No Such User Found.").toString()
        } else{
            ResponseUtil.toResponse(status = 200, data = user, error = null).toString()
        }
    }

    @POST                       //CREATE New User
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    fun addUser(userData : String?) : String {
        return if(userData.isNullOrBlank()){
            ResponseUtil.toResponse(status = 400, data = null, error = "Bad Request").toString()
        }else{
            val user = userService.addUser(userData)
            if(user != null) {
                ResponseUtil.toResponse(status = 200, data = user, error = null).toString()
            }else{
                ResponseUtil.toResponse(status = 500, data = null, error = "Internal Server Error").toString()
            }
        }
    }

    @PUT                //UPDATE A Particular User
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun updateUser(@PathParam("id") id : Int, userData: String?) : String {
        val existingUser = userService.getUserById(id)
        return if(existingUser != null){
            if(userData.isNullOrBlank()) {
                ResponseUtil.toResponse(status = 400, data = null, error = "Bad Request").toString()
            }else{
                val updatedUser = userService.updateUser(existingUser,userData)
                if(updatedUser != null){
                    ResponseUtil.toResponse(status = 200, data = updatedUser, error = null).toString()
                }else{
                    ResponseUtil.toResponse(status = 500, data = null, error = "Internal Server Error.").toString()
                }
            }
        }else{
            ResponseUtil.toResponse(status = 400, data = null, error ="Bad Request. No Such User Found.").toString()
        }
    }

    @DELETE                         //DELETE A Particular User
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun deleteUser(@PathParam("id") id : Int) : String {
        val user : User? = userService.getUserById(id)
        return if(user == null){
            ResponseUtil.toResponse(status = 400, data = null, error ="Bad Request. No Such User Found.").toString()
        }else{
            val deletedUser = userService.deleteUser(user)
            ResponseUtil.toResponse(status = 200, data = deletedUser, error = null).toString()
        }
    }
}