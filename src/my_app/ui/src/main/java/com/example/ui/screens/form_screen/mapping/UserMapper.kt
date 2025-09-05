package com.example.ui.screens.form_screen.mapping

import com.example.ui.model.UserModel
import com.example.ui.screens.form_screen.form.UserForm

object UserMapper {
    fun toModel(form: UserForm) = UserModel(
        id = form.id,
        name = form.name,
        surname = form.surname,
        secondName = form.secondName.ifEmpty { null },
        password = form.password,
        phoneNumber = form.phoneNumber,
        role = form.role,
    )

    fun toForm(model: UserModel) = UserForm(
        id = model.id,
        name = model.name,
        surname = model.surname,
        secondName = model.secondName ?: "",
        password = model.password,
        phoneNumber = model.phoneNumber,
        role = model.role,
    )
}