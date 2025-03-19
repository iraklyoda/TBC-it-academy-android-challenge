package com.example.challenge.presentation.screen.connection

sealed interface ConnectionUiEvent {
    data object NavigateToLogIn : ConnectionUiEvent
    data class ShowErrorSnackBar(val errorMessage: String?) : ConnectionUiEvent
}