package ru.tpu.pretpu.core.base.di.entrypoint

import ru.tpu.pretpu.core.base.infrastructure.ConfigChangerImpl
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tpu.pretpu.core.credentials.data.repository.CredentialsRepositoryImpl

/*
* Использование
* */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface LanguageChangeEntryPoint {
    val repository: CredentialsRepositoryImpl
    val configChanger: ConfigChangerImpl
}