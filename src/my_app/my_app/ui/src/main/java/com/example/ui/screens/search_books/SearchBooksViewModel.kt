package com.example.ui.screens.search_books

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ui.mapping.BookMapper
import com.example.ui.network.BookApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchBooksViewModel @Inject constructor(
    private val bookApi: BookApi,
    private val mapper: BookMapper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow<SearchBooksState>(SearchBooksState.Loading)
    val state: StateFlow<SearchBooksState> = _state

    private val sentence = savedStateHandle.get<String>("query") ?: ""

    fun loadBooks() {
        viewModelScope.launch {
            _state.value = SearchBooksState.Loading
            try {
                val books = bookApi.getBooksBySentence(sentence).map { mapper.toUi(it) }
                _state.value = SearchBooksState.Success(books)
            } catch (e: Exception) {
                _state.value = SearchBooksState.Error(e.message ?: "Unknown error")
            }
        }
    }
}