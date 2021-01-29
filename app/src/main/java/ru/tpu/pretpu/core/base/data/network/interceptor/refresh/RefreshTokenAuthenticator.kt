package ru.tpu.pretpu.core.base.data.network.interceptor.refresh

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ru.tpu.pretpu.core.base.data.network.interceptor.refresh.api.RefreshTokenApi
import ru.tpu.pretpu.core.credentials.domain.repository.CredentialsRepository
import javax.inject.Inject

/**
 * Класс отвечает за обновление токена при получении ошибки 401
 * (не реализован должным образом, оставлен на будущее как пример)
 * */
class RefreshTokenAuthenticator
@Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: CredentialsRepository,
    private val refreshTokenApi: RefreshTokenApi
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val updatedToken = getNewToken()
        return if (updatedToken == null) {
            // Если возобновление токена не успешно, открывает окно логина и возвращаем null
                //context.start
            null
        }
        else {
            // Токен возобновлен успешно, повторяем запрос с новым токеном
            response.request.newBuilder()
                .header("Authorization", "Bearer $updatedToken")
                .build()
        }
    }


    // Синхронный запрос на получение нового токена,
    // при успешном запросе токен сохраняется
    private fun getNewToken(): String? {
        // Возвращаем null, если в репозитории нет рефреш токена
        // (юзер не нажал "запомнить меня")
        val refreshToken = repository.refreshToken
        if (refreshToken == null) return refreshToken

        // При наличии рефреш токена, делаем синхронный запрос на обновление токена.
        val tokens = refreshTokenApi.getTokenFromRefresh(refreshToken).execute().body()
        tokens?.let {
            // Сохраняем токены в памяти, если результат успешен
            repository.refreshToken = it.refreshToken
            repository.token = it.token
        }
        // Возвращается либо объект с токенами, либо null
        return tokens?.token
    }

    /*
    * Если не сработает (параметр token в getRefreshToken перезапишется интерсептором),
    * можно попробовать присвоить в репозитории token = refreshToken: тогда
    * интерсептор отправит запрос getRefreshToken с рефреш токеном в заголовке.
    * */
}