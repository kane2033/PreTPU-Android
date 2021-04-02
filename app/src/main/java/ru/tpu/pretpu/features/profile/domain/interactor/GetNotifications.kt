package ru.tpu.pretpu.features.profile.domain.interactor

import ru.tpu.pretpu.core.base.domain.exception.Failure
import ru.tpu.pretpu.core.base.domain.functional.Either
import ru.tpu.pretpu.core.base.domain.interactor.None
import ru.tpu.pretpu.core.base.domain.interactor.UseCase
import ru.tpu.pretpu.core.credentials.domain.repository.CredentialsRepository
import ru.tpu.pretpu.features.profile.domain.entity.Notification
import ru.tpu.pretpu.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject


class GetNotifications
@Inject constructor(
    private val credentialsRepository: CredentialsRepository,
    private val profileRepository: ProfileRepository
) : UseCase<List<Notification>, None>() {
    override suspend fun run(params: None): Either<Failure, List<Notification>> {
        val email = credentialsRepository.email
        return if (email != null) {
            profileRepository.getNotifications(email)
        } else {
            Either.Left(Failure.MissingContentFailure)
        }
    }

}