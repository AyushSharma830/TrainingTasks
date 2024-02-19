import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/users")
class UserResource {
    private val userService = UserService()

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllUsers() : List<String>?{
        val users : List<String>? = userService.getAllUsers()
        if(users != null)   return users
        return null
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUserById(@PathParam("id") id : Int?) : String? {
        val user : String? = userService.getUserById(id)
        if(user != null)    return user
        return "No Data Found"
    }

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    fun addUser(data : String?) : Response{
        val messege = userService.addUser(data)
        return Response.ok(messege.entity).type(MediaType.APPLICATION_JSON).build()
    }
}