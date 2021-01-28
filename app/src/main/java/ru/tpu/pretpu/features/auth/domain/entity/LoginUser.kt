package ru.tpu.pretpu.features.auth.domain.entity

import ru.tpu.pretpu.core.base.domain.validation.Validator
import ru.tpu.pretpu.core.base.domain.validation.rules.Checked
import ru.tpu.pretpu.core.base.domain.validation.rules.Email
import ru.tpu.pretpu.core.base.domain.validation.rules.NotEmpty
import ru.tpu.pretpu.core.base.domain.validation.rules.Password

data class LoginUser(
    @Email val email: String,
    @Password val password: String,
    val rememberMe: Boolean = false
): Validator() {
}