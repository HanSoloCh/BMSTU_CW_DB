package com.example.ui.screens.form_screen.form

import java.util.UUID

data class PublisherForm(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val description: String = "",
    val foundationYear: String = "",
    val email: String = "",
    val phoneNumber: String = "",
) : ValidatableForm {
    override fun validate(): Map<String, String?> {
        val errors = mutableMapOf<String, String?>()
        errors["name"] = if (name.isBlank()) "Название обязательно" else null

        return errors
    }
}