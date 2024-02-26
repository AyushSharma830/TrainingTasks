package validationServices

import models.School

class SchoolValidation {

    fun validateFields(school : School){
        validateName(school.name)
        validateLocation(school.location)
    }

    private fun validateName(name : String?){
        require(!name.isNullOrBlank()) { "School Name can not be Null or Blank." }
    }

    private fun validateLocation(location : String?){
        require(!location.isNullOrBlank()) { "School Location can not be Null or Blank." }
    }
}