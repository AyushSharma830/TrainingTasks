package resource

import utility.ResponseUtil
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import constraints.Constraints

@Path("/schools")
class SchoolResource {
    private val schoolService = service.SchoolService()
    private val studentService = service.StudentService()

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllSchools(
        @QueryParam("limit") limit : Int?,
        @QueryParam("offset") offset: Int?
    ) : String{
        return try{
            val schools = schoolService.getAllSchools(limit, offset)
            ResponseUtil.success(data = schools)
        }catch(e : IllegalArgumentException){
            ResponseUtil.error(status = 400, error = e.message)
        }
    }

    @GET
    @Path("/school/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getSchoolById(@PathParam("id") id : String) : String{
        return try{
            val school = schoolService.getSchoolById(id)
            if(school != null){
                ResponseUtil.success(data = school)
            }else{
                ResponseUtil.error(status = 400, error = Constraints.NO_SCHOOL_ERROR)
            }
        }catch(e : IllegalArgumentException){
            ResponseUtil.error(status = 400, error = e.message)
        }
    }

    @POST
    @Path("/school")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun addSchool(schoolData : String?) : String{
        return try{
            if(schoolData.isNullOrBlank()){
                ResponseUtil.error(status = 400, error = Constraints.BAD_REQ_ERROR)
            }else{
                val addedSchool = schoolService.addSchool(schoolData)
                if(addedSchool != null){
                    ResponseUtil.success(data = addedSchool)
                }else{
                    ResponseUtil.error(status = 500, error = Constraints.INT_SERVER_ERROR)
                }
            }
        }catch(e : IllegalArgumentException){
            ResponseUtil.error(status = 400, error = e.message)
        }
    }

    @PUT
    @Path("/school/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun partialUpdateSchool(@PathParam("id") id : String, schoolData : String?) : String{
        return try{
            val existingSchool = schoolService.getSchoolById(id)
            if(existingSchool != null){
                if(!schoolData.isNullOrBlank()){
                    val updatedSchool = schoolService.partialUpdateSchool(existingSchool, schoolData)
                    if(updatedSchool != null){
                        ResponseUtil.success(data = updatedSchool)
                    }else{
                        ResponseUtil.error(status = 500, error = Constraints.INT_SERVER_ERROR)
                    }
                }else{
                    ResponseUtil.error(status = 400, error = Constraints.BAD_REQ_ERROR)
                }
            }else{
                ResponseUtil.error(status = 400, error = Constraints.NO_SCHOOL_ERROR)
            }
        }catch(e : IllegalArgumentException){
            ResponseUtil.error(status = 400, error = e.message)
        }
    }

    @DELETE
    @Path("/school/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun deleteSchool(@PathParam("id") id : String) : String{
        return try{
            val deletedSchool = schoolService.deleteSchool(id)
            if(deletedSchool != null){
                studentService.deleteStudentsBySchoolId(id)
                ResponseUtil.success(data = deletedSchool)
            }else{
                ResponseUtil.error(status = 400, error = Constraints.NO_SCHOOL_ERROR)
            }
        }catch(e : IllegalArgumentException){
            ResponseUtil.error(status = 400, error = e.message)
        }
    }

    @POST
    @Path("/school/{id}/student")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun addStudent(@PathParam("id") id : String, studentData : String?) : String{
        return try{
            if(studentData.isNullOrBlank()){
                ResponseUtil.error(status = 400, error = Constraints.BAD_REQ_ERROR)
            }else{
                val school = schoolService.getSchoolById(id)
                if(school != null){
                    val student = studentService.addStudent(studentData, school)
                    if(student != null){
                        ResponseUtil.success(data = student)
                    }else{
                        ResponseUtil.error(status = 500, error = Constraints.INT_SERVER_ERROR)
                    }
                }else{
                    ResponseUtil.error(status = 400, error = Constraints.NO_SCHOOL_ERROR)
                }
            }
        }catch(e : IllegalArgumentException){
            ResponseUtil.error(status = 400, error = e.message)
        }
    }

    @GET
    @Path("/school/{id}/students")
    @Produces(MediaType.APPLICATION_JSON)
    fun getStudentsBySchoolId(
        @PathParam("id") id : String,
        @QueryParam("limit") limit : Int?,
        @QueryParam("offset") offset: Int?
        ) : String{
        return try{
            val school = schoolService.getSchoolById(id)
            if(school != null){
                val students = studentService.getStudentsBySchoolId(id, limit, offset)
                ResponseUtil.success(data = students)
            }else{
                ResponseUtil.error(status = 400, error = Constraints.NO_SCHOOL_ERROR)
            }
        }catch(e : IllegalArgumentException){
            ResponseUtil.error(status = 400, error = e.message)
        }
    }
}