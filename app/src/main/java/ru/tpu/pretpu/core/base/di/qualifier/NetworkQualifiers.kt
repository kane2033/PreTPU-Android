package ru.tpu.pretpu.core.base.di.qualifier

import javax.inject.Qualifier

object NetworkQualifiers {

    // Retrofit без интерспеторов
    // аутентификации и токенов
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RetrofitWithAuth

    // Retrofit c интерсепторами
    // аутентификации и токенов
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RetrofitWithoutAuth
}