package com.example.challenge.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.challenge.data.common.HandleResponse
import com.example.challenge.data.repository.connection.ConnectionsRepositoryImpl
import com.example.challenge.data.repository.datastore.DataStoreRepositoryImpl
import com.example.challenge.data.repository.log_in.LogInRepositoryImpl
import com.example.challenge.data.service.connection.ConnectionsService
import com.example.challenge.domain.repository.connection.ConnectionsRepository
import com.example.challenge.domain.repository.datastore.DataStoreRepository
import com.example.challenge.domain.repository.log_in.LogInRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindDataStoreRepository(dataStoreRepository: DataStoreRepositoryImpl): DataStoreRepository

    @Binds
    @Singleton
    fun bindConnectionsRepository(connectionsRepository: ConnectionsRepositoryImpl): ConnectionsRepository

    @Binds
    @Singleton
    fun bindLoginRepository(loginRepository: LogInRepositoryImpl): LogInRepository
}