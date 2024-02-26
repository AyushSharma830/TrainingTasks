package validationServices

import models.Student
import repository.StudentRepository

class StudentValidation {
    private val studentRepository = StudentRepository()

    fun validateFields(student : Student, existingStudentId : String?){
        validateName(student.name)
        validateEmail(student.email)
        validateRollNo(student.rollNo)
        validateEmailUniqueness(student.email!!, existingStudentId)
        validateRollNoUniqueness(student.rollNo!!, existingStudentId, student.schoolInfo!!.schoolId!!)
    }

    private fun validateName(name : String?){
        require(!name.isNullOrBlank())  { "Student Name can not be Null or Blank." }
    }

    private fun validateEmail(email : String?){
        require(!email.isNullOrBlank()) { "Student Email can not be Null or Blank." }
        require(email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))) { "Invalid email format" }
    }

    private fun validateRollNo(rollNo : String?){
        require(!rollNo.isNullOrBlank()) { "Student RollNo. can not be Null or Blank." }
    }

    private fun validateEmailUniqueness(email : String, existingStudentId: String?){
        require(!studentRepository.validateEmailUniqueness(email, existingStudentId)) { "Student Email should be unique." }
    }

    private fun validateRollNoUniqueness(rollNo : String, existingStudentId: String?, schoolId: String){
        require(!studentRepository.validateRollNoUniqueness(rollNo, existingStudentId, schoolId)) { "Student's Roll No in the School must be Unique." }
    }
}