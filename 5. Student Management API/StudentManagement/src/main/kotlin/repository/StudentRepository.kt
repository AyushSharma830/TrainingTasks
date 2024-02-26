package repository

import models.Student

class StudentRepository {
    companion object{
        var students = mutableListOf<Student>()
    }

    private fun sortStudents(){
        students = students.sortedBy { it.createdAt }.toMutableList()
    }

    fun validateEmailUniqueness(email : String, existingStudentId : String?) : Boolean{
        return students.any{ it.email == email && it.id != existingStudentId }
    }

    fun validateRollNoUniqueness(rollNo : String, existingStudentId: String?, schoolId : String) : Boolean{
        return students.any{ it.schoolInfo!!.schoolId == schoolId && it.rollNo == rollNo && it.id != existingStudentId }
    }

    fun deleteStudentsBySchoolId(schoolId : String){
        try{
            students.removeIf{ it.schoolInfo!!.schoolId  == schoolId}
            sortStudents()
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun getStudentsBySchoolId(id : String, limit: Int?, offset : Int?) : List<Student>{
        return try{
            val listOfStudents = students.filter{ it.schoolInfo!!.schoolId == id }
            val start = (offset ?: 0) * (limit ?: 1)
            val end = if (limit != null) minOf(start + limit, students.size) else students.size
            listOfStudents.subList(start, end)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun getAllStudents(limit : Int?, offset : Int?) : List<Student>{
        return try{
            val start = (offset ?: 0) * (limit ?: 1)
            val end = if (limit != null) minOf(start + limit, students.size) else students.size
            students.subList(start, end)
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun getStudentById(id : String) : Student?{
        return try{
            students.firstOrNull{ it.id == id }
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun addStudent(student: Student) : Student?{
        return try {
            students.add(student)
            sortStudents()
            students.firstOrNull { it.id == student.id }
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun partialUpdateStudent(existingStudent : Student, student : Student) : Student?{
        return try{
            val indexOfExistingStudent = students.indexOf(existingStudent)
            students[indexOfExistingStudent] = student
            students.firstOrNull{ it.id == student.id }
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }

    fun deleteStudent(id : String) : Student?{
        return try{
            val iterator = students.iterator()
            while(iterator.hasNext()){
                val student = iterator.next()
                if(student.id == id){
                    iterator.remove()
                    sortStudents()
                    return student
                }
            }
            null
        }catch(e : IllegalArgumentException){
            throw(IllegalArgumentException(e.message))
        }
    }
}