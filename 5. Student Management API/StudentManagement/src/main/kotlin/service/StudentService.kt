package service

import models.Student
import models.LiteSchool
import repository.StudentRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import constants.Constants
import models.School
import org.json.JSONObject
import java.util.UUID


class StudentService() {
    private val studentRepository = StudentRepository()
    private val schoolService = SchoolService()
    private val validateStudents = validationServices.StudentValidation()

    fun deleteStudentsBySchoolId(schoolId : String){
        try{
            studentRepository.deleteStudentsBySchoolId(schoolId)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun getStudentsBySchoolId(id : String, limit : Int?, offset : Int?) : List<Student>{
        return try{
           studentRepository.getStudentsBySchoolId(id, limit, offset)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun getAllStudents(limit : Int?, offset : Int?) : List<Student>{
        return try{
            studentRepository.getAllStudents(limit, offset)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun getStudentById(id : String) : Student?{
        return try{
            studentRepository.getStudentById(id)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun addStudent(studentData : String, school : School) : Student {
        return try{
            val student = Gson().fromJson(studentData, Student::class.java)
            val liteSchoolJson = JSONObject()
            liteSchoolJson.put("schoolId", school.id)
            liteSchoolJson.put("schoolName", school.name)
            val schoolInfo = Gson().fromJson(liteSchoolJson.toString(), LiteSchool::class.java)
            student.schoolInfo = schoolInfo
            student.id = UUID.randomUUID().toString()
            student.createdAt = System.currentTimeMillis()
            validateStudents.validateFields(student, null)
            studentRepository.addStudent(student)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun partialUpdateStudent(existingStudentId: String, studentData: String) : Student?{
        return try{
            val studentJson = JSONObject(studentData)
            var key = studentJson["key"].toString()
            var value = if(studentJson.isNull("value")) null else studentJson["value"]
            if(key == "schoolId"){
                val newSchoolInfo = schoolService.getSchoolById(value.toString())
                if (newSchoolInfo != null) {
                    val liteSchoolJson = JSONObject()
                    liteSchoolJson.put("schoolId", newSchoolInfo.id)
                    liteSchoolJson.put("schoolName", newSchoolInfo.name)
                    key = "schoolInfo"
                    value = liteSchoolJson
                } else {
                    throw (IllegalArgumentException("Bad Request, No Such School Found."))
                }
            }
            when(key){
                "name" -> {
                    validateStudents.validateName(value?.toString())
                }
                "email" -> {
                    validateStudents.validateEmail(value?.toString())
                    validateStudents.validateEmailUniqueness(value.toString(), existingStudentId)
                }
                "rollNo" -> {
                    validateStudents.validateRollNo(value?.toString())
                    validateStudents.validateRollNoUniqueness(value.toString(), existingStudentId, null)
                }
                "schoolInfo" -> {
                    val student = studentRepository.getStudentById(existingStudentId)
                    if(student != null){
                        student.schoolInfo = GsonBuilder().serializeNulls().create().fromJson(value.toString(), LiteSchool::class.java)
                        validateStudents.validateFields(student, existingStudentId)
                    }else{
                        throw(IllegalArgumentException(Constants.NO_STUDENT_ERROR))
                    }
                }
                else -> {
                    throw(IllegalArgumentException("No Valid Key."))
                }
            }
            studentRepository.partialUpdateStudent(existingStudentId, key, value)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun deleteStudent(id : String) : Student?{
        return try{
            studentRepository.deleteStudent(id)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }
}