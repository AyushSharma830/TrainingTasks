package repository

import com.google.gson.GsonBuilder
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.Projections
import com.mongodb.client.model.ReturnDocument
import com.mongodb.client.model.Updates
import constants.Constants
import models.School
import models.Student
import org.bson.Document
import org.litote.kmongo.findOne
import org.litote.kmongo.findOneById
import setupConnection
import javax.inject.Inject
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class StudentRepository @Inject constructor(private val db : MongoDatabase) {

    var students: MongoCollection<Document> = db.getCollection( "students")

    fun validateEmailUniqueness(email : String, existingStudentId : String?) : Boolean{
        return students.findOne(Filters.and(
            Filters.eq("email", email),
            Filters.ne("id", existingStudentId)
        )) == null
    }

    fun validateRollNoUniqueness(rollNo : String, existingStudentId: String?, schoolId : String?) : Boolean{
        var existingStudentSchoolId : String? = null
        if(schoolId.isNullOrBlank()){
            val studentDoc = students.findOneById(existingStudentId!!)
            if(studentDoc != null){
                val student = GsonBuilder().serializeNulls().create().fromJson(studentDoc.toJson(), Student::class.java)
                existingStudentSchoolId = student.schoolInfo!!.schoolId!!
            }else{
                throw(IllegalArgumentException(Constants.NO_STUDENT_ERROR))
            }
        }
        val id = schoolId ?: existingStudentSchoolId
        return students.findOne(Filters.and(
            Filters.eq("rollNo", rollNo),
            Filters.eq("schoolInfo.schoolId", id),
            Filters.ne("id", existingStudentId)
        )) == null
    }

    fun deleteStudentsBySchoolId(schoolId : String){
        try{
            students.deleteMany(Filters.eq("schoolInfo.schoolId", schoolId))
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun getStudentsBySchoolId(id : String, limit: Int?, offset : Int?) : List<Student>{
        return try{
            val projection = Projections.exclude("schoolInfo")
            val sortedStudents = students
                .find(Filters.eq("schoolInfo.schoolId", id))
                .projection(projection)
                .sort(Document("createdAt", 1))
            val start = (offset ?: 0) * (limit ?: 1)
            val paginatedStudents = if (limit != null) sortedStudents.skip(start).limit(limit) else sortedStudents
            val listOfStudents = mutableListOf<Student>()
            val iterator = paginatedStudents.iterator()
            while(iterator.hasNext()){
                val student = GsonBuilder().serializeNulls().create().fromJson(iterator.next().toJson(), Student::class.java)
                listOfStudents.add(student)
            }
            listOfStudents
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun getAllStudents(limit : Int?, offset : Int?) : List<Student>{
        return try{
            val sortedStudents = students
                .find()
                .sort(Document("createdAt", 1))
            val start = (offset ?: 0) * (limit ?: 1)
            val paginatedStudents = if (limit != null) sortedStudents.skip(start).limit(limit) else sortedStudents
            val listOfStudents = mutableListOf<Student>()
            val iterator = paginatedStudents.iterator()
            while(iterator.hasNext()){
                val student = GsonBuilder().serializeNulls().create().fromJson(iterator.next().toJson(), Student::class.java)
                listOfStudents.add(student)
            }
            listOfStudents
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun getStudentById(id : String) : Student?{
        return try{
            val student = students.findOneById(id)
            GsonBuilder().serializeNulls().create().fromJson(student?.toJson(), Student::class.java)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun addStudent(student: Student) : Student{
        return try {
            val doc = Document.parse(student.toString())
            doc["_id"] = student.id
            students.insertOne(doc)
            student
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun <T> partialUpdateStudent(existingStudentId : String, key : String, value : T) : Student?{
        return try{
            val options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
            val doc = if(key == "schoolInfo") Document.parse(value.toString()) else value
            val updatedStudent = students.findOneAndUpdate(Filters.eq("id", existingStudentId), Updates.set(key, doc), options)
            GsonBuilder().serializeNulls().create().fromJson(updatedStudent?.toJson(), Student::class.java)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun deleteStudent(id : String) : Student?{
        return try{
            val deletedStudent = students.findOneAndDelete(Filters.eq("id", id))
            GsonBuilder().serializeNulls().create().fromJson(deletedStudent?.toJson(), Student::class.java)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }
}