package com.example.challenge.presentation.screen.log_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge.domain.common.Resource
import com.example.challenge.domain.usecase.datastore.SaveTokenUseCase
import com.example.challenge.domain.usecase.log_in.LogInUseCase
import com.example.challenge.domain.usecase.validator.EmailValidatorUseCase
import com.example.challenge.domain.usecase.validator.PasswordValidatorUseCase
import com.example.challenge.presentation.event.log_in.LogInEvent
import com.example.challenge.presentation.state.log_in.LogInState
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
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val emailValidator: EmailValidatorUseCase,
    private val passwordValidator: PasswordValidatorUseCase
) : ViewModel() {
    private val _logInState = MutableStateFlow(LogInState())
    val logInState: StateFlow<LogInState> = _logInState.asStateFlow()

    private val _uiEvent = Channel<LogInUiEvent>()
    val uiEvent: Flow<LogInUiEvent> get() = _uiEvent.receiveAsFlow()

    fun onEvent(event: LogInEvent) {
        when (event) {
            is LogInEvent.LogIn -> validateForm(email = event.email, password = event.password)
            is LogInEvent.ResetErrorMessage -> updateErrorMessage(message = null)
        }
    }

    private fun logIn(email: String, password: String) {
        viewModelScope.launch {
            logInUseCase(email = email, password = password).collect {
                when (it) {
                    is Resource.Loading -> _logInState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }

                    is Resource.Success -> {
                        _logInState.update { currentState -> currentState.copy(accessToken = it.data.accessToken) }
                        _uiEvent.send(LogInUiEvent.NavigateToConnections)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                }
            }
        }
    }

    private fun validateForm(email: String, password: String) {
        val isEmailValid = emailValidator(email)
        val isPasswordValid = passwordValidator(password)

        val areFieldsValid =
            listOf(isEmailValid, isPasswordValid)
                .all { it }

        if (!areFieldsValid) {
            updateErrorMessage(message = "Fields are not valid!")
            return
        }

        logIn(email = email, password = password)
    }

    private fun updateErrorMessage(message: String?) {
        _logInState.update { currentState -> currentState.copy(errorMessage = message) }
    }

    sealed interface LogInUiEvent {
        data object NavigateToConnections : LogInUiEvent
    }
}



