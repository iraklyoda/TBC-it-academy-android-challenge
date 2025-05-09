package com.example.challenge.presentation.screen.connection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge.presentation.mapper.connection.toPresenter
import com.example.challenge.domain.common.Resource
import com.example.challenge.domain.usecase.connection.GetConnectionsUseCase
import com.example.challenge.domain.usecase.datastore.ClearPreferencesUseCase
import com.example.challenge.presentation.event.conection.ConnectionEvent
import com.example.challenge.presentation.state.connection.ConnectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionsViewModel @Inject constructor(
    private val getConnectionsUseCase: GetConnectionsUseCase,
    private val clearDataStoreUseCase: ClearPreferencesUseCase
) :
    ViewModel() {
    private val _connectionState = MutableStateFlow(ConnectionState())
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _uiEvent = Channel<ConnectionUiEvent>()
    val uiEvent: Flow<ConnectionUiEvent> get() = _uiEvent.receiveAsFlow()

    fun onEvent(event: ConnectionEvent) {
        when (event) {
            is ConnectionEvent.FetchConnections -> fetchConnections()
            is ConnectionEvent.LogOut -> logOut()
        }
    }

    private fun fetchConnections() {
        viewModelScope.launch {
            getConnectionsUseCase().collect {
                when (it) {
                    is Resource.Loading -> _connectionState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }

                    is Resource.Success -> {
                        _connectionState.update { currentState -> currentState.copy(connections = it.data.map { it.toPresenter() }) }
                    }

                    is Resource.Error -> {
                        updateErrorMessage(message = it.errorMessage)
                        _uiEvent.send(ConnectionUiEvent.ShowErrorSnackBar(connectionState.value.errorMessage))
                    }
                }
            }
        }
    }

    private fun logOut(){
        viewModelScope.launch {
            clearDataStoreUseCase()
            _uiEvent.send(ConnectionUiEvent.NavigateToLogIn)
        }
    }

    private fun updateErrorMessage(message: String?) {
        _connectionState.update { currentState -> currentState.copy(errorMessage = message) }
    }
}