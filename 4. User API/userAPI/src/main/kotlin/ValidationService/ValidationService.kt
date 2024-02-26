package ValidationService

import User.User
import UserRepository.UserRepository

class ValidationService {
    private val userRepository = UserRepository()

    fun validateFields(user : User, existingUserId : String?) {
        validateName(user.name)
        validateEmail(user.email)
        validateAge(user.age)
        validateEmailUniqueness(user.email!!,existingUserId)
    }

    private fun validateName(name: String?) {
        require( name != null && name!!.isNotBlank()) { "Name can not be Null or Blank." }
    }

    private fun validateEmail(email: String?) {
        require( email != null && email!!.isNotBlank()) { "Email can not be Null or Blank." }
        require(email!!.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))) { "Invalid email format" }
    }

    private fun validateAge(age: Int?) {
        require( age != null && age!! >= 0 && age!! <= 150) { "Age can not be Null and should be greater than 0 and less than 150." }
    }

    private fun validateEmailUniqueness(email: String, existingUserId: String?) {
        require(!userRepository.validateEmailUniqueness(email,existingUserId)) { "Email should be unique." }
    }
}