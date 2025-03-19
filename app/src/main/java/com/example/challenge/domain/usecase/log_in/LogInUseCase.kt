package com.example.challenge.domain.usecase.log_in

import com.example.challenge.domain.mapper.handleSuccess
import com.example.challenge.domain.repository.log_in.LogInRepository
import com.example.challenge.domain.usecase.datastore.SavePreferenceUseCase
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val logInRepository: LogInRepository,
    private val saveTokenUseCase: SavePreferenceUseCase
) {
    suspend operator fun invoke(email: String, password: String) =
        logInRepository.logIn(email = email, password = password).handleSuccess { data ->
            saveTokenUseCase(token = data.accessToken)
        }
}