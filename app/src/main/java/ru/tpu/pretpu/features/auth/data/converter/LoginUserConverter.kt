package ru.tpu.pretpu.features.auth.data.converter

import ru.tpu.pretpu.core.credentials.domain.entity.Credentials
import ru.tpu.pretpu.core.credentials.domain.entity.Language
import ru.tpu.pretpu.core.credentials.domain.entity.User
import ru.tpu.pretpu.features.auth.data.model.LoginUserDto
import ru.tpu.pretpu.features.auth.domain.entity.LoginUser

fun LoginUserDto.toCredentials() = Credentials(
    this.token,
    this.refreshToken,
    User(
        this.user.email,
        this.user.firstName,
        this.user.groupName,
        Language(null, this.user.language)
    )
)