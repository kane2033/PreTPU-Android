package ru.tpu.pretpu.features.news.domain.interactor

import ru.tpu.pretpu.core.base.domain.exception.Failure
import ru.tpu.pretpu.core.base.domain.functional.Either
import ru.tpu.pretpu.core.base.domain.interactor.None
import ru.tpu.pretpu.core.base.domain.interactor.UseCase
import ru.tpu.pretpu.core.credentials.domain.entity.User
import ru.tpu.pretpu.core.credentials.domain.repository.CredentialsRepository
import javax.inject.Inject

class GetHeaderUserInfo
@Inject constructor(private val credentialsRepository: CredentialsRepository) :
    UseCase<User, None>() {
    override suspend fun run(params: None): Either<Failure, User> {
        val user = credentialsRepository.user
        return if (user != null) Either.Right(user) else Either.Left(Failure.MissingContentFailure)
    }
}