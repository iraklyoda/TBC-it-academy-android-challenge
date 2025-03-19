package com.example.challenge.presentation.event.conection

sealed class ConnectionEvent {
    data object FetchConnections : ConnectionEvent()
    data object LogOut : ConnectionEvent()
    data object ResetErrorMessage : ConnectionEvent()
}