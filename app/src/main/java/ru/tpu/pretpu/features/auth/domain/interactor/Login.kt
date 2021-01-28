package ru.tpu.pretpu.features.auth.domain.interactor

import ru.tpu.pretpu.core.base.domain.exception.Failure
import ru.tpu.pretpu.core.base.domain.functional.Either
import ru.tpu.pretpu.core.base.domain.functional.flatMap
import ru.tpu.pretpu.core.base.domain.functional.onSuccess
import ru.tpu.pretpu.core.base.domain.interactor.UseCase
import ru.tpu.pretpu.core.credentials.domain.entity.Credentials
import ru.tpu.pretpu.core.credentials.domain.entity.User
import ru.tpu.pretpu.core.credentials.domain.repository.CredentialsRepository
import ru.tpu.pretpu.features.auth.domain.entity.LoginUser
import ru.tpu.pretpu.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * [UseCase], использующийся для логина:
 * [AuthRepository] возвращает результат авторизации (токен),
 * [CredentialsRepository] сохраняет юзера в память для дальнейшего использования
 * */
class Login
@Inject constructor(private val authRepository: AuthRepository,
                    private val credentialsRepository: CredentialsRepository
) : UseCase<Credentials, LoginUser>() {
    override suspend fun run(params: LoginUser): Either<Failure, Credentials> {
        return params.validate(params).flatMap {
            // Делаем запрос на сервис на получение токена
            val result = authRepository.login(params)
            // Сохраняем токен в репозиторий токенов, если не возникло ошибки
            result.onSuccess {
                credentialsRepository.credentials = it
            }
        }
    }
}