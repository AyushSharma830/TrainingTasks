package repository

import com.google.gson.GsonBuilder
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import com.mongodb.client.model.Updates
import models.School
import org.bson.Document
import org.litote.kmongo.findOneById

import setupConnection

class SchoolRepository {
    companion object{
        private val db = setupConnection()
        var schools: MongoCollection<Document> = db.getCollection( "schools")
    }

    fun getAllSchools(limit : Int?, offset : Int?) : List<School>{
        return try{
            val start = (offset ?: 0) * (limit ?: 1)
            val sortedSchools = schools.find().sort(Document("createdAt",1))
            val paginatedSchools = if(limit != null) sortedSchools.skip(start).limit(limit) else sortedSchools
//            paginatedSchools.map { document -> GsonBuilder().serializeNulls().create().fromJson(document.toJson(), School::class.java) }.into(ArrayList<School>())
            val listOfSchools = mutableListOf<School>()
            val iterator = paginatedSchools.iterator()
            while(iterator.hasNext()){
                val school = GsonBuilder().serializeNulls().create().fromJson(iterator.next().toJson(),School::class.java)
                listOfSchools.add(school)
            }
            listOfSchools
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun getSchoolById(id : String) : School?{
        return try{
            val school = schools.findOneById(id)
            GsonBuilder().serializeNulls().create().fromJson(school?.toJson(), School::class.java)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

       fun addSchool(school: School): School{
           return try {
               val doc = Document.parse(school.toString())
               doc["_id"] = school.id
               schools.insertOne(doc)
               school
           } catch (e: IllegalArgumentException) {
               throw (IllegalArgumentException(e.message))
           }
    }

    fun <T> partialUpdateSchool(existingSchoolId : String, key : String, value : T) : School?{
        return try{
            val options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
            val updateSchool = schools.findOneAndUpdate(Filters.eq("id", existingSchoolId), Updates.set(key, value), options)
            GsonBuilder().serializeNulls().create().fromJson(updateSchool?.toJson(), School::class.java)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun deleteSchool(id : String) : School?{
        return try{
            val deletedSchool = schools.findOneAndDelete(Filters.eq("id", id))
            GsonBuilder().serializeNulls().create().fromJson(deletedSchool?.toJson(), School::class.java)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }
}