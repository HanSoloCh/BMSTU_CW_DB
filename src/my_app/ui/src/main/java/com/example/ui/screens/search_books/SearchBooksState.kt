package com.example.ui.screens.search_books

import com.example.ui.model.BookModel

sealed class SearchBooksState {
    object Loading : SearchBooksState()
    data class Success(val books: List<BookModel>) : SearchBooksState()
    data class Error(val message: String) : SearchBooksState()
}