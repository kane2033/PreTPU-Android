package ru.tpu.pretpu.core.credentials.data.repository

import android.content.SharedPreferences
import ru.tpu.pretpu.core.base.extension.putString
import ru.tpu.pretpu.core.base.extension.putStrings
import ru.tpu.pretpu.core.base.extension.removeStrings
import ru.tpu.pretpu.core.credentials.domain.repository.CredentialsRepository
import ru.tpu.pretpu.core.credentials.domain.entity.Credentials
import ru.tpu.pretpu.core.credentials.domain.entity.Language
import ru.tpu.pretpu.core.credentials.domain.entity.User
import javax.inject.Inject

/**
 * Репозиторий информации о пользователе,
 * реализованный с помощью [SharedPreferences].
 * */
class CredentialsRepositoryImpl
@Inject constructor(private val sharedPreferences: SharedPreferences) : CredentialsRepository {

    // Ключи получения строк из sharedPreferences
    companion object {
        private const val TOKEN_KEY = "TOKEN"
        private const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN"
        private const val EMAIL_KEY = "EMAIL"
        private const val FIRST_NAME_KEY = "FIRST_NAME"
        private const val LANGUAGE_ID_KEY = "LANGUAGE_ID"
        private const val LANGUAGE_NAME_KEY = "LANGUAGE_NAME"
        private const val GROUP_KEY = "GROUP"
    }

    /**
     * Основные используемые объекты при работе с пользователями, составляемые из строк,
     * доставаемых с помощью [SharedPreferences]
     * */

    override var credentials: Credentials?
        get() = Credentials(
            token,
            refreshToken,
            User(email, firstName, group, Language(languageId, languageName))
        )
        set(value) {
            if (value != null) {
                sharedPreferences.putStrings(
                    mapOf(
                        TOKEN_KEY to value.token,
                        REFRESH_TOKEN_KEY to value.refreshToken,

                        EMAIL_KEY to value.user?.email,
                        FIRST_NAME_KEY to value.user?.firstName,
                        GROUP_KEY to value.user?.groupName,

                        LANGUAGE_ID_KEY to value.user?.language?.languageId,
                        LANGUAGE_NAME_KEY to value.user?.language?.languageName
                    )
                )
                token = value.token
                refreshToken = value.refreshToken
                user = value.user
            } else {
                sharedPreferences.removeStrings(
                    TOKEN_KEY,
                    REFRESH_TOKEN_KEY,

                    EMAIL_KEY,
                    FIRST_NAME_KEY,
                    GROUP_KEY,

                    LANGUAGE_ID_KEY,
                    LANGUAGE_NAME_KEY
                )
            }
        }

    override var user: User?
        get() = User(email, firstName, group, language)
        set(value) {
            if (value != null) {
                sharedPreferences.putStrings(
                    mapOf(
                        EMAIL_KEY to value.email,
                        FIRST_NAME_KEY to value.firstName,
                        GROUP_KEY to value.groupName,

                        LANGUAGE_ID_KEY to value.language?.languageId,
                        LANGUAGE_NAME_KEY to value.language?.languageName
                    )
                )
            } else {
                sharedPreferences.removeStrings(
                    EMAIL_KEY,
                    FIRST_NAME_KEY,
                    GROUP_KEY,

                    LANGUAGE_ID_KEY,
                    LANGUAGE_NAME_KEY
                )
            }
        }

    override var language: Language?
        get() = Language(languageId, languageName)
        set(value) {
            if (value != null) {
                sharedPreferences.putStrings(
                    mapOf(
                        LANGUAGE_ID_KEY to value.languageId,
                        LANGUAGE_NAME_KEY to value.languageName
                    )
                )
            } else {
                sharedPreferences.removeStrings(LANGUAGE_ID_KEY, LANGUAGE_NAME_KEY)
            }

        }

    /**
     * Строки, из которых составляются объекты личных данных пользователя
     * */

    override var token: String?
        get() = sharedPreferences.getString(TOKEN_KEY, null)
        set(value) {
            sharedPreferences.putString(TOKEN_KEY, value)
        }

    override var refreshToken: String?
        get() = sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
        set(value) {
            sharedPreferences.putString(REFRESH_TOKEN_KEY, value)
        }

    override var email: String?
        get() = sharedPreferences.getString(EMAIL_KEY, null)
        set(value) {
            sharedPreferences.putString(EMAIL_KEY, value)
        }

    override var firstName: String?
        get() = sharedPreferences.getString(FIRST_NAME_KEY, null)
        set(value) {
            sharedPreferences.putString(FIRST_NAME_KEY, value)
        }

    override var languageId: String?
        get() = sharedPreferences.getString(LANGUAGE_ID_KEY, null)
        set(value) {
            sharedPreferences.putString(LANGUAGE_ID_KEY, value)
        }

    override var languageName: String?
        get() = sharedPreferences.getString(LANGUAGE_NAME_KEY, null)
        set(value) {
            sharedPreferences.putString(LANGUAGE_NAME_KEY, value)
        }

    override var group: String?
        get() = sharedPreferences.getString(GROUP_KEY, null)
        set(value) {
            sharedPreferences.putString(GROUP_KEY, value)
        }

}