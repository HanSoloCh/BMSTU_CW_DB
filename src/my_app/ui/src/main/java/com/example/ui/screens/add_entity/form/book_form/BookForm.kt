package com.example.ui.screens.add_entity.form.book_form

data class BookForm(
    val title: String = "",
    val annotation: String = "",
    val authors: List<String> = listOf(""),
    val publisher: String = "",
    val publicationYear: String = "",
    val codeISBN: String = "",
    val bbk: String = "",
    val mediaType: String = "",
    val volume: String = "",
    val language: String = "",
    val originalLanguage: String = "",
    val copies: String = ""
)