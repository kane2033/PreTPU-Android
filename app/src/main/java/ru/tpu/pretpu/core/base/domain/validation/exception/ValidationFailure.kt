package ru.tpu.pretpu.core.base.domain.validation.exception

import ru.tpu.pretpu.core.base.domain.exception.Failure
import kotlin.reflect.KProperty1

sealed class ValidationFailure(val symbol: String): Failure.FeatureFailure() {

    object EmptyField: ValidationFailure("EMPTY_FIELD")

    object LengthExceeded: ValidationFailure("LENGTH_EXCEEDED")

    object EmailIncorrect: ValidationFailure("EMAIL_INCORRECT")

    object PasswordIncorrect: ValidationFailure("PASSWORD_INCORRECT")

    object NotChecked: ValidationFailure("NOT_CHECKED")

    class FormFailures<T: Any>(val validationFailures: MutableMap<KProperty1<T, *>, ValidationFailure>): ValidationFailure("")
}