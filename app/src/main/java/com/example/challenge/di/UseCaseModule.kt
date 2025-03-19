package com.example.challenge.di

import com.example.challenge.domain.preferences.PreferencesStorage
import com.example.challenge.domain.repository.connection.ConnectionsRepository
import com.example.challenge.domain.repository.log_in.LogInRepository
import com.example.challenge.domain.usecase.connection.GetConnectionsUseCase
import com.example.challenge.domain.usecase.datastore.ClearPreferencesUseCase
import com.example.challenge.domain.usecase.datastore.GetPreferenceUseCase
import com.example.challenge.domain.usecase.datastore.SavePreferenceUseCase
import com.example.challenge.domain.usecase.log_in.LogInUseCase
import com.example.challenge.domain.usecase.validator.EmailValidatorUseCase
import com.example.challenge.domain.usecase.validator.PasswordValidatorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideLogInUseCase(
        logInRepository: LogInRepository,
        saveTokenUseCase: SavePreferenceUseCase
    ): LogInUseCase {
        return LogInUseCase(logInRepository = logInRepository, saveTokenUseCase = saveTokenUseCase)
    }

    @Singleton
    @Provides
    fun provideEmailValidatorUseCase(
    ): EmailValidatorUseCase {
        return EmailValidatorUseCase()
    }

    @Singleton
    @Provides
    fun providePasswordValidatorUseCase(
    ): PasswordValidatorUseCase {
        return PasswordValidatorUseCase()
    }

    @Singleton
    @Provides
    fun provideSaveTokenUseCase(
        preferencesStorage: PreferencesStorage
    ): SavePreferenceUseCase {
        return SavePreferenceUseCase(preferencesStorage = preferencesStorage)
    }

    @Singleton
    @Provides
    fun provideGetConnectionsUseCase(
        connectionsRepository: ConnectionsRepository
    ): GetConnectionsUseCase {
        return GetConnectionsUseCase(connectionsRepository = connectionsRepository)
    }

    @Singleton
    @Provides
    fun provideClearDataStoreUseCase(
        preferencesStorage: PreferencesStorage
    ): ClearPreferencesUseCase {
        return ClearPreferencesUseCase(preferencesStorage = preferencesStorage)
    }

    @Singleton
    @Provides
    fun provideGetTokenUseCase(
        preferencesStorage: PreferencesStorage
    ): GetPreferenceUseCase {
        return GetPreferenceUseCase(preferencesStorage = preferencesStorage)
    }
}