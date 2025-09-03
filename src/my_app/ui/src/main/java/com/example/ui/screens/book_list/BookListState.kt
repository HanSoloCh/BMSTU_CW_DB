package com.example.ui.screens.book_list

import com.example.ui.model.BookModel


sealed class BookListState {
    object Loading : BookListState()
    data class Success(val books: List<BookModel>, val canLoadMore: Boolean) : BookListState()
    data class Error(val message: String) : BookListState()
}