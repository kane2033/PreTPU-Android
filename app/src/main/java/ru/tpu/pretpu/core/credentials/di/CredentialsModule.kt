package ru.tpu.pretpu.core.credentials.di

import android.content.Context
import android.content.SharedPreferences
import ru.tpu.pretpu.core.base.domain.infrastructure.ConfigChanger
import ru.tpu.pretpu.core.base.infrastructure.ConfigChangerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.tpu.pretpu.core.credentials.data.repository.CredentialsRepositoryImpl
import ru.tpu.pretpu.core.credentials.domain.repository.CredentialsRepository
import javax.inject.Singleton


/**
 * [Module], ответственный за репозиторий информации о юзере.
 * */
@Module
@InstallIn(SingletonComponent::class)
object CredentialsModule {

    @Provides
    @Singleton
    fun provideCredentialsRepository(repository: CredentialsRepositoryImpl):
            CredentialsRepository = repository

}