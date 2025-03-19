package com.example.challenge.domain.usecase.datastore


import com.example.challenge.domain.preferences.AppPreferenceKeys
import com.example.challenge.domain.preferences.PreferencesStorage
import javax.inject.Inject

class GetPreferenceUseCase @Inject constructor(private val preferencesStorage: PreferencesStorage) {
    suspend operator fun invoke() = preferencesStorage.readValue(AppPreferenceKeys.TOKEN_KEY, "")
}