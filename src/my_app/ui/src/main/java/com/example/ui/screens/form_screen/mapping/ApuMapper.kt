package com.example.ui.screens.form_screen.mapping

import com.example.ui.model.ApuModel
import com.example.ui.screens.form_screen.form.ApuForm

object ApuMapper {
    fun toModel(form: ApuForm) = ApuModel(
        id = form.id,
        term = form.term,
        bbkModel = form.bbk!!,
    )

    fun toForm(model: ApuModel) = ApuForm(
        id = model.id,
        term = model.term,
        bbk = model.bbkModel
    )
}