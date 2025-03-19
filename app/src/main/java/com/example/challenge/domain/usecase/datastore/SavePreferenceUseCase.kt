package com.example.challenge.domain.usecase.datastore

import com.example.challenge.domain.preferences.AppPreferenceKeys
import com.example.challenge.domain.preferences.PreferencesStorage
import javax.inject.Inject

class SavePreferenceUseCase @Inject constructor(private val preferencesStorage: PreferencesStorage) {
    suspend operator fun invoke(token: String) {
        preferencesStorage.saveValue(key = AppPreferenceKeys.TOKEN_KEY, value = token)
    }
}