package resource

import constraints.Constraints
import service.StudentService
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

import utility.ResponseUtil

@Path("/students")
class StudentResource {
    private val studentService = StudentService()

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllStudents(
        @QueryParam("limit") limit : Int?,
        @QueryParam("offset") offset: Int?
    ) : String{
        return try{
            val schools = studentService.getAllStudents(limit, offset)
            ResponseUtil.success(data = schools)
        }catch(e : IllegalArgumentException){
            ResponseUtil.error(status = 400, error = e.message)
        }
    }

    @GET
    @Path("/student/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getStudentById(@PathParam("id") id : String) : String{
        return try{
            val student = studentService.getStudentById(id)
            if(student != null){
                ResponseUtil.success(data = student)
            }else{
                ResponseUtil.error(status = 400, error = Constraints.NO_STUDENT_ERROR)
            }
        }catch(e : IllegalArgumentException){
            ResponseUtil.error(status = 400, error = e.message)
        }
    }

    @PUT
    @Path("/student/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun partialUpdateStudent(@PathParam("id") id : String, studentData : String?) : String{
        return try{
            val existingStudent = studentService.getStudentById(id)
            if(existingStudent != null){
                if(!studentData.isNullOrBlank()){
                    val updatedStudent = studentService.partialUpdateStudent(existingStudent, studentData)
                    if(updatedStudent != null){
                        ResponseUtil.success(data = updatedStudent)
                    }else{
                        ResponseUtil.error(status = 500, error = Constraints.INT_SERVER_ERROR)
                    }
                }else{
                    ResponseUtil.error(status = 400, error = Constraints.BAD_REQ_ERROR)
                }
            }else{
                ResponseUtil.error(status = 400, error = Constraints.NO_STUDENT_ERROR)
            }
        }catch(e : IllegalArgumentException){
            ResponseUtil.error(status = 400, error = e.message)
        }
    }

    @DELETE
    @Path("student/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun deleteStudentById(@PathParam("id") id : String) : String {
        return try{
            val deletedStudent = studentService.deleteStudent(id)
            if(deletedStudent != null){
                ResponseUtil.success(data = deletedStudent)
            }else{
                ResponseUtil.error(status = 400, error = Constraints.NO_STUDENT_ERROR)
            }
        }catch(e : IllegalArgumentException){
            ResponseUtil.error(status = 400, error = e.message)
        }
    }
}