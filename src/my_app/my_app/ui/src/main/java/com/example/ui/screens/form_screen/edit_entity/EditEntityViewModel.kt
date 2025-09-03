package com.example.ui.screens.form_screen.edit_entity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ui.mapping.ApuMapper
import com.example.ui.mapping.AuthorMapper
import com.example.ui.mapping.BbkMapper
import com.example.ui.mapping.BookMapper
import com.example.ui.mapping.PublisherMapper
import com.example.ui.mapping.UserMapper
import com.example.ui.network.ApuApi
import com.example.ui.network.AuthorApi
import com.example.ui.network.BbkApi
import com.example.ui.network.BookApi
import com.example.ui.network.PublisherApi
import com.example.ui.network.UserApi
import com.example.ui.screens.form_screen.edit_entity.edit_form_screen.EditEntityType
import com.example.ui.screens.form_screen.form.ApuForm
import com.example.ui.screens.form_screen.form.AuthorForm
import com.example.ui.screens.form_screen.form.BbkForm
import com.example.ui.screens.form_screen.form.BookForm
import com.example.ui.screens.form_screen.form.PublisherForm
import com.example.ui.screens.form_screen.form.UserForm
import com.example.ui.screens.form_screen.form.ValidatableForm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEntityViewModel @Inject constructor(
    private val bookApi: BookApi,
    private val authorApi: AuthorApi,
    private val publisherApi: PublisherApi,
    private val bbkApi: BbkApi,
    private val apuApi: ApuApi,
    private val userApi: UserApi,
    private val bookMapper: BookMapper,
    private val authorMapper: AuthorMapper,
    private val publisherMapper: PublisherMapper,
    private val bbkMapper: BbkMapper,
    private val apuMapper: ApuMapper,
    private val userMapper: UserMapper
) : ViewModel() {
    private val _state = MutableStateFlow<EditEntityState>(EditEntityState.Empty)
    val state: StateFlow<EditEntityState> = _state

    var selectedType by mutableStateOf(EditEntityType.BOOK)
    var submitted by mutableStateOf(false)
    var buttonVisibility by mutableStateOf(false)


    var bookForm by mutableStateOf(BookForm())
    var authorForm by mutableStateOf(AuthorForm())
    var publisherForm by mutableStateOf(PublisherForm())
    var apuForm by mutableStateOf(ApuForm())
    var bbkForm by mutableStateOf(BbkForm())
    var userForm by mutableStateOf(UserForm())

    fun editEntity() {
        viewModelScope.launch {
            _state.value = EditEntityState.Loading
            try {
                viewModelScope.launch {
                    _state.value = EditEntityState.Loading
                    try {
                        val form = when (selectedType) {
                            EditEntityType.AUTHOR -> authorForm
                            EditEntityType.BOOK -> bookForm
                            EditEntityType.PUBLISHER -> publisherForm
                            EditEntityType.BBK -> bbkForm
                            EditEntityType.APU -> apuForm
                            EditEntityType.USER -> userForm
                        }
                        if (form.isValid()) {
                            editEntity(form)
                            _state.value = EditEntityState.Success
                        } else {
                            _state.value = EditEntityState.Error("Форма содержит ошибки")
                        }
                    } catch (e: Exception) {
                        submitted = true
                        _state.value = EditEntityState.Error(e.message.toString())
                    }
                }
                _state.value = EditEntityState.Success
            } catch (e: Exception) {
                _state.value = EditEntityState.Error(e.message.toString())
            }
        }
    }

    fun deleteEntity() {
        viewModelScope.launch {
            _state.value = EditEntityState.Loading
            try {
                when (selectedType) {
                    EditEntityType.AUTHOR -> {
                        authorApi.deleteAuthor(authorForm.id)
                    }

                    EditEntityType.BOOK -> {
                        bookApi.deleteBook(bookForm.id)
                    }

                    EditEntityType.PUBLISHER -> {
                        publisherApi.deletePublisher(publisherForm.id)
                    }

                    EditEntityType.BBK -> {
                        bbkApi.deleteBbk(bbkForm.id)

                    }

                    EditEntityType.APU -> {
                        apuApi.deleteApu(apuForm.id)
                    }

                    EditEntityType.USER -> {
                        userApi.deleteUser(userForm.id)
                    }
                }
                _state.value = EditEntityState.Success
            } catch (e: Exception) {
                _state.value = EditEntityState.Error(e.message.toString())
            }
        }
    }

    fun changeEntityType() {
        submitted = false
        _state.value = EditEntityState.Empty
    }

    private suspend fun editEntity(form: ValidatableForm) {
        when (form) {
            is BookForm -> editBook(form)
            is AuthorForm -> editAuthor(form)
            is PublisherForm -> editPublisher(form)
            is BbkForm -> editBbk(form)
            is ApuForm -> editApu(form)
            is UserForm -> editUser(form)
        }
    }


    // Update
    private suspend fun editBook(bookForm: BookForm) {
        val bookModel = com.example.ui.screens.form_screen.mapping.BookMapper.toModel(bookForm)
        bookApi.updateBook(bookMapper.toDto(bookModel))
    }

    private suspend fun editAuthor(authorForm: AuthorForm) {
        val authorModel =
            com.example.ui.screens.form_screen.mapping.AuthorMapper.toModel(authorForm)
        authorApi.updateAuthor(authorMapper.toDto(authorModel))
    }

    private suspend fun editPublisher(publisherForm: PublisherForm) {
        val publisherModel =
            com.example.ui.screens.form_screen.mapping.PublisherMapper.toModel(publisherForm)
        publisherApi.updatePublisher(publisherMapper.toDto(publisherModel))
    }

    private suspend fun editApu(apuForm: ApuForm) {
        val apuModel = com.example.ui.screens.form_screen.mapping.ApuMapper.toModel(apuForm)
        apuApi.updateApu(apuMapper.toDto(apuModel))
    }

    private suspend fun editBbk(bbkForm: BbkForm) {
        val bbkModel = com.example.ui.screens.form_screen.mapping.BbkMapper.toModel(bbkForm)
        bbkApi.updateBbk(bbkMapper.toDto(bbkModel))
    }

    private suspend fun editUser(userForm: UserForm) {
        val userModel = com.example.ui.screens.form_screen.mapping.UserMapper.toModel(userForm)
        userApi.updateUser(userMapper.toDto(userModel))
    }
}