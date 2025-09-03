package com.example.ui.screens.form_screen.mapping

import com.example.ui.model.AuthorModel
import com.example.ui.screens.form_screen.form.AuthorForm

object AuthorMapper {
    fun toModel(form: AuthorForm) = AuthorModel(
        id = form.id,
        name = form.name
    )

    fun toForm(model: AuthorModel) = AuthorForm(
        id = model.id,
        name = model.name
    )
}