package com.example.ui.screens.profile_screen

sealed class ProfileState {
    object Empty : ProfileState()
    object Loading : ProfileState()
    object Success : ProfileState()
    data class Error(val message: String) : ProfileState()
}