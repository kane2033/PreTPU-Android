package ru.tpu.pretpu.core.base.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.tpu.pretpu.core.base.domain.infrastructure.ConfigChanger
import ru.tpu.pretpu.core.base.infrastructure.ConfigChangerImpl
import javax.inject.Singleton

/**
 * [Module], который используется всем приложением.
 * */
@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    private const val REPOSITORY_KEY = "REPOSITORY"

    // SharedPreferences для репозитория токенов
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context):
            SharedPreferences = context.getSharedPreferences(REPOSITORY_KEY, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideConfigChanger(configChanger: ConfigChangerImpl):
            ConfigChanger = configChanger

}