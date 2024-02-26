package repository

import models.School

class SchoolRepository {
    companion object{
        var schools = mutableListOf<School>()
    }

    private fun sortSchools(){
         schools = schools.sortedBy { it.createdAt }.toMutableList()
    }

    fun getAllSchools(limit : Int?, offset : Int?) : List<School>{
        return try{
            val start = (offset ?: 0) * (limit ?: 1)
            val end = if (limit != null) minOf(start + limit, schools.size) else schools.size
            schools.subList(start, end)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun getSchoolById(id : String) : School?{
        return try{
            schools.firstOrNull{ it.id == id }
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun addSchool(school : School) : School?{
        return try{
            schools.add(school)
            sortSchools()
            schools.firstOrNull{ it.id == school.id }
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun partialUpdateSchool(existingSchool : School, school : School) : School?{
        return try{
            val indexOfExistingSchool = schools.indexOf(existingSchool)
            schools[indexOfExistingSchool] = school
            schools.firstOrNull{ it.id == school.id }
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun deleteSchool(id : String) : School?{
        return try{
            val iterator = schools.iterator()
            while(iterator.hasNext()){
                val school = iterator.next()
                if(school.id == id){
                    iterator.remove()
                    sortSchools()
                    return school
                }
            }
            null
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }
}