package com.example.challenge.presentation.event.log_in

sealed class LogInEvent {
    data class LogIn(val email: String, val password: String) : LogInEvent()
    data object ResetErrorMessage : LogInEvent()
}