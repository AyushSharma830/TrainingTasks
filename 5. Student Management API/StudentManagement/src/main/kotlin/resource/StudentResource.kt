package resource

import constants.Constants
import service.StudentService
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

import utility.ResponseUtil
import javax.inject.Inject

@Path("/students")
class StudentResource @Inject constructor(private val studentService: StudentService) {

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
                ResponseUtil.error(status = 400, error = Constants.NO_STUDENT_ERROR)
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
            if(!studentData.isNullOrBlank()){
                val updatedStudent = studentService.partialUpdateStudent(id, studentData)
                if(updatedStudent != null){
                    ResponseUtil.success(data = updatedStudent)
                }else{
                    ResponseUtil.error(status = 400, error = Constants.NO_SCHOOL_ERROR)                }
            }else{
                ResponseUtil.error(status = 400, error = Constants.BAD_REQ_ERROR)
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
                ResponseUtil.error(status = 400, error = Constants.NO_STUDENT_ERROR)
            }
        }catch(e : IllegalArgumentException){
            ResponseUtil.error(status = 400, error = e.message)
        }
    }
}