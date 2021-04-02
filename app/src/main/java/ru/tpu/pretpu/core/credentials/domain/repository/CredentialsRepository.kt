package ru.tpu.pretpu.core.credentials.domain.repository

import ru.tpu.pretpu.core.credentials.domain.entity.Credentials
import ru.tpu.pretpu.core.credentials.domain.entity.Group
import ru.tpu.pretpu.core.credentials.domain.entity.Language
import ru.tpu.pretpu.core.credentials.domain.entity.User

interface CredentialsRepository {

    var credentials: Credentials?

    var user: User?

    var group: Group?

    var language: Language?

    var token: String?

    var refreshToken: String?

    var email: String?

    var firstName: String?

    var languageId: String?

    var languageName: String?

    var groupId: String?

    var groupName: String?

    var internalGroupId: String?
}