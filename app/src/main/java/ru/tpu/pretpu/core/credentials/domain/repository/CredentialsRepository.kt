package ru.tpu.pretpu.core.credentials.domain.repository

import ru.tpu.pretpu.core.credentials.domain.entity.Credentials
import ru.tpu.pretpu.core.credentials.domain.entity.Language
import ru.tpu.pretpu.core.credentials.domain.entity.User

interface CredentialsRepository {

    var credentials: Credentials?

    var user: User?

    var language: Language?

    var token: String?

    var refreshToken: String?

    var email: String?

    var firstName: String?

    var languageId: String?

    var languageName: String?

    var group: String?
}