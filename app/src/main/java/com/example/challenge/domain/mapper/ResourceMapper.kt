package com.example.challenge.domain.mapper

import com.example.challenge.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

suspend fun <DTO, DOMAIN> Flow<Resource<DTO>>.asResource(
    onSuccess: suspend (DTO) -> DOMAIN,
): Flow<Resource<DOMAIN>> {
    return this.map {
        when (it) {
            is Resource.Success -> Resource.Success(data = onSuccess.invoke(it.data))
            is Resource.Error -> Resource.Error(errorMessage = it.errorMessage)
            is Resource.Loading -> Resource.Loading(loading = it.loading)
        }
    }
}


fun <T> Flow<Resource<T>>.handleSuccess(onSuccess: suspend (T) -> Unit): Flow<Resource<T>> {
    return this.map { resource ->
        when (resource) {
            is Resource.Loading -> Resource.Loading(resource.loading)
            is Resource.Error -> Resource.Error(resource.errorMessage)
            is Resource.Success -> {
                onSuccess(resource.data)
                Resource.Success(resource.data)
            }
        }
    }
}