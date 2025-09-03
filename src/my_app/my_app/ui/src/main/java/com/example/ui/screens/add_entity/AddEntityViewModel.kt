package com.example.ui.screens.add_entity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ui.mapping.AuthorMapper
import com.example.ui.mapping.BbkMapper
import com.example.ui.mapping.BookMapper
import com.example.ui.mapping.PublisherMapper
import com.example.ui.model.AuthorModel
import com.example.ui.model.BookModel
import com.example.ui.model.PublisherModel
import com.example.ui.network.AuthorApi
import com.example.ui.network.BbkApi
import com.example.ui.network.BookApi
import com.example.ui.network.PublisherApi
import com.example.ui.screens.add_entity.form.EntityType
import com.example.ui.screens.add_entity.form.author_form.AuthorForm
import com.example.ui.screens.add_entity.form.book_form.BookForm
import com.example.ui.screens.add_entity.form.publisher_form.PublisherForm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEntityViewModel @Inject constructor(
    private val bookApi: BookApi,
    private val authorApi: AuthorApi,
    private val publisherApi: PublisherApi,
    private val bbkApi: BbkApi,
    private val bookMapper: BookMapper,
    private val authorMapper: AuthorMapper,
    private val publisherMapper: PublisherMapper,
    private val bbkMapper: BbkMapper
) : ViewModel() {
    private val _state = MutableStateFlow<AddEntityState>(AddEntityState.Empty)
    val state: StateFlow<AddEntityState> = _state

    var selectedType by mutableStateOf(EntityType.AUTHOR)

    var bookForm by mutableStateOf(BookForm())
    var authorForm by mutableStateOf(AuthorForm())
    var publisherForm by mutableStateOf(PublisherForm())


    fun addEntity() {
        viewModelScope.launch {
            _state.value = AddEntityState.Loading
            try {
                when (selectedType) {
                    EntityType.AUTHOR -> {
                        addAuthor(authorForm)
                    }

                    EntityType.BOOK -> {
                        addBook(bookForm)
                    }

                    EntityType.PUBLISHER -> {
                        addPublisher(publisherForm)
                    }

                    EntityType.BBK -> {}
                    EntityType.APU -> {}
                }
                _state.value = AddEntityState.Success
                clearFields()
            } catch (e: Exception) {
                _state.value = AddEntityState.Error(e.message.toString())
            }
        }
    }

    private suspend fun clearFields() {
        bookForm = BookForm()
        authorForm = AuthorForm()
        publisherForm = PublisherForm()
    }

    private suspend fun addBook(bookForm: BookForm) {
        val authorModels = bookForm.authors.map { author ->
            authorMapper.toUi(authorApi.getAuthor(author))
        }
        val publisherModel = publisherMapper.toUi(publisherApi.getPublisher(bookForm.publisher))
        val bbkModel = bbkMapper.toUi(bbkApi.getBbk(bookForm.bbk))
        val bookModel = BookModel(
            id = UUID.randomUUID(),
            title = bookForm.title,
            annotation = bookForm.annotation.ifEmpty { null },
            authors = authorModels,
            publisherModel = publisherModel,
            publicationYear = bookForm.publicationYear.toIntOrNull(),
            codeISBN = bookForm.codeISBN,
            bbkModel = bbkModel,
            mediaType = bookForm.mediaType.ifEmpty { null },
            volume = bookForm.volume.ifEmpty { null },
            language = bookForm.language.ifEmpty { null },
            originalLanguage = bookForm.originalLanguage.ifEmpty { null },
            copies = bookForm.copies.toInt(),
            availableCopies = bookForm.copies.toInt(),
        )
        bookApi.createBook(bookMapper.toDto(bookModel))
    }

    private suspend fun addAuthor(authorForm: AuthorForm) {
        val authorModel = AuthorModel(
            id = UUID.randomUUID(),
            name = authorForm.name
        )
        authorApi.createAuthor(authorMapper.toDto(authorModel))
    }

    private suspend fun addPublisher(publisherForm: PublisherForm) {
        val publisherModel = PublisherModel(
            id = UUID.randomUUID(),
            name = publisherForm.name,
            description = publisherForm.description.ifEmpty { null },
            foundationYear = publisherForm.foundationYear.toIntOrNull(),
            email = publisherForm.email.ifEmpty { null },
            phoneNumber = publisherForm.phoneNumber.ifEmpty { null },
        )
        publisherApi.createPublisher(publisherMapper.toDto(publisherModel))
    }
}