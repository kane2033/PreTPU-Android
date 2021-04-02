package ru.tpu.pretpu.features.profile.domain.repository

import ru.tpu.pretpu.core.base.domain.exception.Failure
import ru.tpu.pretpu.core.base.domain.functional.Either
import ru.tpu.pretpu.core.credentials.domain.entity.User
import ru.tpu.pretpu.features.profile.domain.entity.Notification

interface ProfileRepository {
    suspend fun getPersonalInfo(email: String): Either<Failure, User>
    suspend fun getDocuments(email: String)
    suspend fun getNotifications(email: String): Either<Failure, List<Notification>>
}