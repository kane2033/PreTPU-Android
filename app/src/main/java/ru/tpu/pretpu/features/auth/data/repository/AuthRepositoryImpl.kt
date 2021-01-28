package ru.tpu.pretpu.features.auth.data.repository

import ru.tpu.pretpu.core.base.data.network.SafeApiCall
import ru.tpu.pretpu.core.base.domain.exception.Failure
import ru.tpu.pretpu.core.base.domain.functional.Either
import ru.tpu.pretpu.core.credentials.domain.entity.Credentials
import ru.tpu.pretpu.core.credentials.domain.entity.User
import ru.tpu.pretpu.features.auth.data.network.AuthApi
import ru.tpu.pretpu.features.auth.domain.entity.LoginUser
import ru.tpu.pretpu.features.auth.domain.repository.AuthRepository
import ru.tpu.pretpu.features.auth.data.converter.toCredentials
import javax.inject.Inject

/**
 * Реализация репозитория [AuthRepository],
 * возвращающая результат логина/регистрации пользователя
 * в UseCase.
 * */
class AuthRepositoryImpl
@Inject constructor(private val authApi: AuthApi, private val safeApiCall: SafeApiCall) :
    AuthRepository {

    override suspend fun login(user: LoginUser): Either<Failure, Credentials> =
        safeApiCall.safeApiResult(
            { authApi.loginAsync(user) },
            { it.toCredentials() }
        )

    override suspend fun register(user: User): Either<Failure, Credentials> =
        TODO()

}