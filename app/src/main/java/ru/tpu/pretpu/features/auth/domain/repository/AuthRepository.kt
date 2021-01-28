package ru.tpu.pretpu.features.auth.domain.repository

import ru.tpu.pretpu.core.base.domain.exception.Failure
import ru.tpu.pretpu.core.base.domain.functional.Either
import ru.tpu.pretpu.core.credentials.domain.entity.Credentials
import ru.tpu.pretpu.core.credentials.domain.entity.User
import ru.tpu.pretpu.features.auth.domain.entity.LoginUser

interface AuthRepository {

    suspend fun login(user: LoginUser): Either<Failure, Credentials>

    suspend fun register(newUser: User): Either<Failure, Credentials>
}