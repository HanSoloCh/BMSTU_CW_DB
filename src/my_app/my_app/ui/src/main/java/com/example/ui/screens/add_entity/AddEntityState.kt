package com.example.ui.screens.add_entity

sealed class AddEntityState {
    object Empty : AddEntityState()
    object Loading : AddEntityState()
    object Success : AddEntityState()
    data class Error(val message: String) : AddEntityState()
}