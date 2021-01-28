package ru.tpu.pretpu.core.base.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.tpu.pretpu.core.credentials.domain.repository.CredentialsRepository
import java.util.*
import javax.inject.Inject

/**
* Добавление bearer токена и языка в каждый запрос.
 * @property credentialsRepository - репозиторий, хранящий токен (shared pref. или др.)
* */
class TokenLanguageInterceptor
    @Inject constructor(private val credentialsRepository: CredentialsRepository): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = credentialsRepository.token ?: ""
        val languageId = credentialsRepository.languageId ?: "" // ?: Locale.getDefault().language
        val newRequest = chain.request()
            .newBuilder()
            .header("Authorization", "Bearer $token")
            .header("Accept-Language", languageId) //язык
            .build()

        return chain.proceed(newRequest)
    }
}