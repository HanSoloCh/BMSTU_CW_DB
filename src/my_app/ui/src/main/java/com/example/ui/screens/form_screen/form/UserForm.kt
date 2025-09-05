package com.example.ui.screens.form_screen.form

import com.example.ui.common.enums.UserRole
import java.util.UUID

data class UserForm(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val surname: String = "",
    val secondName: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val role: UserRole = UserRole.READER,
) : ValidatableForm {
    override fun validate(): Map<String, String?> {
        val errors = mutableMapOf<String, String?>()
        errors["name"] = if (name.isBlank()) "Имя обязательно" else null
        errors["surname"] = if (surname.isBlank()) "Фамилия обязательна" else null
        errors["password"] = if (surname.isBlank()) "Пароль обязателен" else null

        errors["phoneNumber"] = checkPhone(phoneNumber)

        return errors
    }

    fun checkPhone(phoneNumber: String): String? {
        if (phoneNumber.isBlank()) return "Номер телефона обязателен"
        val phoneRegex = "^\\+?[78][0-9]{10}$".toRegex()
        if (!phoneNumber.matches(phoneRegex)) return "Некорректный номер телефона"
        return null
    }

}