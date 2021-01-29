package ru.tpu.pretpu.core.base.di

import ru.tpu.pretpu.BuildConfig
import ru.tpu.pretpu.core.base.data.network.interceptor.TokenLanguageInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.tpu.pretpu.core.base.data.network.interceptor.refresh.RefreshTokenAuthenticator
import ru.tpu.pretpu.core.base.data.network.interceptor.refresh.api.RefreshTokenApi
import ru.tpu.pretpu.core.base.data.network.interceptor.refresh.api.RefreshTokenApiImpl
import javax.inject.Singleton

/**
 * [Module], предоставляющий сетевые компоненты
 * (в данном случае Retrofit).
 * */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://internationals.tpu.ru:8080/api/"

    @Provides
    @Singleton
    fun provideRetrofit(
        tokenLanguageInterceptor: TokenLanguageInterceptor,
        refreshTokenAuthenticator: RefreshTokenAuthenticator
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client(tokenLanguageInterceptor, refreshTokenAuthenticator))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun client(
        tokenLanguageInterceptor: TokenLanguageInterceptor,
        refreshTokenAuthenticator: RefreshTokenAuthenticator
    ): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(tokenLanguageInterceptor)
        okHttpClientBuilder.authenticator(refreshTokenAuthenticator)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRefreshTokenApi(api: RefreshTokenApiImpl): RefreshTokenApi = api

}