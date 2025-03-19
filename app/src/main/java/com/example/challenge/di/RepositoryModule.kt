package com.example.challenge.di

import com.example.challenge.data.local.datastore.DataStoreManagerImpl
import com.example.challenge.data.repository.connection.ConnectionsRepositoryImpl
import com.example.challenge.data.repository.log_in.LogInRepositoryImpl
import com.example.challenge.domain.preferences.PreferencesStorage
import com.example.challenge.domain.repository.connection.ConnectionsRepository
import com.example.challenge.domain.repository.log_in.LogInRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindDataStoreRepository(preferencesStorage: DataStoreManagerImpl): PreferencesStorage

    @Binds
    @Singleton
    fun bindConnectionsRepository(connectionsRepository: ConnectionsRepositoryImpl): ConnectionsRepository

    @Binds
    @Singleton
    fun bindLoginRepository(loginRepository: LogInRepositoryImpl): LogInRepository
}