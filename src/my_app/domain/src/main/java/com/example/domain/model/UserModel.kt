package com.example.domain.model

import com.example.domain.common.Regexes
import com.example.domain.enums.UserRole
import com.example.domain.exception.EmptyStringException
import com.example.domain.exception.InvalidEmailException
import com.example.domain.exception.InvalidPhoneException
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserModel(
    val id: @Contextual UUID = UUID.randomUUID(),
    val name: String,
    val surname: String,
    val secondName: String? = null,
    val password: String,
    val phoneNumber: String,
    val role: UserRole,
) {
    init {
        when {
            name.isBlank() -> throw EmptyStringException("name")
            surname.isBlank() -> throw EmptyStringException("surname")
            secondName != null && secondName.isBlank() -> throw EmptyStringException("secondName")
            password.isBlank() -> throw EmptyStringException("password")
        }
    }


    private fun isValidPhone(phone: String): Boolean {
        return phone.matches(Regexes.PHONE_NUMBER)
    }
}
