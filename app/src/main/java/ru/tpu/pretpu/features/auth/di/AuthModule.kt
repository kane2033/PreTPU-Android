package ru.tpu.pretpu.features.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ru.tpu.pretpu.features.auth.data.network.AuthApi
import ru.tpu.pretpu.features.auth.data.network.AuthApiImpl
import ru.tpu.pretpu.features.auth.data.repository.AuthRepositoryImpl
import ru.tpu.pretpu.features.auth.domain.repository.AuthRepository

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    @ViewModelScoped
    fun provideAuthApi(api: AuthApiImpl): AuthApi = api

    @Provides
    @ViewModelScoped
    fun provideAuthRepository(repository: AuthRepositoryImpl):
            AuthRepository = repository
}