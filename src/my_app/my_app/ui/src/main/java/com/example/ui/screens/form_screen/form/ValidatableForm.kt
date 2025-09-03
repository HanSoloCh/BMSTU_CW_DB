package com.example.ui.screens.form_screen.form

interface ValidatableForm {
    fun validate(): Map<String, String?> // ключ — поле, значение — ошибка (null если ошибки нет)

    fun isValid(): Boolean = validate().values.all { it == null }
}