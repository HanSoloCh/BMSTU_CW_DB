package com.example.ui.screens.form_screen.add_entity

sealed class AddEntityState {
    object Empty : AddEntityState()
    object Loading : AddEntityState()
    object Success : AddEntityState()
    data class Error(val message: String) : AddEntityState()
}