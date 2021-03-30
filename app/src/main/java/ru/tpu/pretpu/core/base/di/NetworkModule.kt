package ru.tpu.pretpu.core.base.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.tpu.pretpu.BuildConfig
import ru.tpu.pretpu.core.base.data.network.interceptor.TokenLanguageInterceptor
import ru.tpu.pretpu.core.base.data.network.interceptor.refresh.RefreshTokenAuthenticator
import ru.tpu.pretpu.core.base.data.network.interceptor.refresh.api.RefreshTokenApi
import ru.tpu.pretpu.core.base.data.network.interceptor.refresh.api.RefreshTokenApiImpl
import ru.tpu.pretpu.core.base.di.qualifier.NetworkQualifiers.RetrofitWithAuth
import ru.tpu.pretpu.core.base.di.qualifier.NetworkQualifiers.RetrofitWithoutAuth
import ru.tpu.pretpu.core.base.domain.Constants
import javax.inject.Singleton

/**
 * [Module], предоставляющий сетевые компоненты
 * (в данном случае Retrofit).
 * */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_API_URL = "${Constants.BASE_URL}/api/"

    @RetrofitWithAuth
    @Provides
    @Singleton
    fun provideRetrofitWithAuth(
        tokenLanguageInterceptor: TokenLanguageInterceptor,
        refreshTokenAuthenticator: RefreshTokenAuthenticator
    ): Retrofit = getBasicRetrofitBuilder()
        .client(client(tokenLanguageInterceptor, refreshTokenAuthenticator))
        .build()


    @RetrofitWithoutAuth
    @Provides
    @Singleton
    fun provideRetrofitWithoutAuth(): Retrofit = getBasicRetrofitBuilder().build()

    private fun getBasicRetrofitBuilder() = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())


    private fun getOkHttpClient() = getDefaultBuilder().build()

    private fun getAuthInterceptorsOkHttpClient(
        tokenLanguageInterceptor: TokenLanguageInterceptor,
        refreshTokenAuthenticator: RefreshTokenAuthenticator
    ) = getDefaultBuilder()
        .addInterceptor(tokenLanguageInterceptor)
        .authenticator(refreshTokenAuthenticator)
        .build()

    private fun getDefaultBuilder(): OkHttpClient.Builder {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder
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