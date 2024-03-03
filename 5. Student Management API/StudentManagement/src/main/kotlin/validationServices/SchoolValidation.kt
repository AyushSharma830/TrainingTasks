package validationServices

import models.School
import javax.inject.Inject

class SchoolValidation @Inject constructor() {

    fun validateFields(school : School){
        validateName(school.name)
        validateLocation(school.location)
    }

    fun validateName(name: String?){
        require(!name.isNullOrBlank()) { "School Name can not be Null or Blank." }
    }

    fun  validateLocation(location: String?){
        require(!location.isNullOrBlank()) { "School Location can not be Null or Blank." }
    }
}