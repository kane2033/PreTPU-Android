package ru.tpu.pretpu.features.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import ru.tpu.pretpu.features.auth.data.network.AuthApi
import ru.tpu.pretpu.features.auth.data.network.AuthApiImpl
import ru.tpu.pretpu.features.auth.data.repository.AuthRepositoryImpl
import ru.tpu.pretpu.features.auth.domain.repository.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object AuthModule {

    @Provides
    @ActivityScoped
    fun provideLoanApi(api: AuthApiImpl): AuthApi = api

    @Provides
    @ActivityScoped
    fun provideAuthRepository(repository: AuthRepositoryImpl):
            AuthRepository = repository
}