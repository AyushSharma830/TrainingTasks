package service

import com.google.gson.Gson
import models.School
import org.json.JSONObject
import repository.SchoolRepository
import validationServices.SchoolValidation
import java.util.UUID

class SchoolService {
    private val schoolRepository = SchoolRepository()
    private val schoolValidation = SchoolValidation()

    fun getAllSchools(limit : Int?, offset : Int?) : List<School>{
        return try{
            schoolRepository.getAllSchools(limit, offset)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun getSchoolById(id : String) : School?{
        return try{
            schoolRepository.getSchoolById(id)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

      fun addSchool(schoolData : String) : School{
        return try{
            val school = Gson().fromJson(schoolData, School::class.java)
            school.id = UUID.randomUUID().toString()
            school.createdAt = System.currentTimeMillis()
            schoolValidation.validateFields(school)
            schoolRepository.addSchool(school)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun partialUpdateSchool(existingSchoolId: String, schoolData: String) : School?{
        return try{
            val schoolJson = JSONObject(schoolData)
            val key = schoolJson["key"].toString()
            val value = if(schoolJson.isNull("value")) null else schoolJson["value"]
            when (key) {
                "name" -> {
                    schoolValidation.validateName(value?.toString())
                }
                "location" -> {
                    schoolValidation.validateLocation(value?.toString())
                }
                else -> {
                    throw(IllegalArgumentException("No Valid Key."))
                }
            }
            schoolRepository.partialUpdateSchool(existingSchoolId, key, value!!)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun deleteSchool(id : String) : School?{
        return try{
            schoolRepository.deleteSchool(id)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }
}