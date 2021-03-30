package ru.tpu.pretpu.core.credentials.domain.interactor

import ru.tpu.pretpu.core.base.domain.exception.Failure
import ru.tpu.pretpu.core.base.domain.functional.Either
import ru.tpu.pretpu.core.base.domain.interactor.None
import ru.tpu.pretpu.core.base.domain.interactor.UseCase
import ru.tpu.pretpu.core.credentials.domain.repository.CredentialsRepository
import javax.inject.Inject

/**
 * [UseCase], использующийся для проверки логина
 * по наличию токена в [CredentialsRepository]
 * */
class UserLoggedIn
@Inject constructor(private val credentialsRepository: CredentialsRepository) :
    UseCase<Boolean, None>() {
    override suspend fun run(params: None): Either<Failure, Boolean> {
        return Either.Right(credentialsRepository.token != null)
    }
}