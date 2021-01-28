package ru.tpu.pretpu.core.base.domain.validation

import android.util.Log
import android.util.Patterns
import ru.tpu.pretpu.core.base.domain.functional.Either
import ru.tpu.pretpu.core.base.domain.validation.exception.ValidationFailure
import ru.tpu.pretpu.core.base.domain.validation.rules.*
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

abstract class Validator() {

    inline fun <reified T: Any> validate(obj: T): Either<ValidationFailure.FormFailures<T>, Boolean> {
        //val fields = obj::class.java.declaredFields
        val properties = obj.javaClass.kotlin.memberProperties
        val validationErrors = mutableMapOf<KProperty1<T, *>, ValidationFailure>()
        // entity::class.java.kotlin.members можно попробовать members у kotlin
        properties.forEach { property ->
            //property.isAccessible = true
            val fieldValue = property.get(obj)
            property.annotations.forEach { annotation ->
                when (annotation) {
                    is Email -> {
                        fieldValue as String
                        if (!Regex(annotation.regex).matches(fieldValue)) {
                            validationErrors[property] = ValidationFailure.EmailIncorrect
                        }
                    }
                    is Password -> {
                        fieldValue as String
                        if (!Regex(annotation.regex).matches(fieldValue)) {
                            validationErrors[property] = ValidationFailure.PasswordIncorrect
                        }
                    }
                    is NotEmpty -> {
                        fieldValue as String
                        if (fieldValue.isEmpty()) {
                            validationErrors[property] = ValidationFailure.EmptyField
                        }
                    }
                    is Max -> {
                        fieldValue as String
                        if (fieldValue.length > annotation.maxLength) {
                            validationErrors[property] = ValidationFailure.LengthExceeded
                        }
                    }
                    is Checked -> {
                        fieldValue as Boolean
                        if (!fieldValue) {
                            validationErrors[property] = ValidationFailure.LengthExceeded
                        }
                    }
                }
            }
        }
        return if (validationErrors.isEmpty()) {
            Either.Right(true)
        } else {
            Either.Left(ValidationFailure.FormFailures(validationErrors))
        }
    }

}