package com.example.ui.screens.form_screen.edit_entity

sealed class EditEntityState {
    object Empty : EditEntityState()
    object Loading : EditEntityState()
    object Success : EditEntityState()
    data class Error(val message: String) : EditEntityState()
}