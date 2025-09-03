package com.example.ui.screens.bbk_books

import com.example.ui.model.BookModel

sealed class BbkBooksState {
    object Loading : BbkBooksState()
    data class Success(val books: List<BookModel>) : BbkBooksState()
    data class Error(val message: String) : BbkBooksState()
}