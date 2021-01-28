package ru.tpu.pretpu.core.base.domain.validation

import org.junit.Test
import org.junit.Assert
import ru.tpu.pretpu.core.base.domain.functional.onFailure
import ru.tpu.pretpu.core.base.domain.validation.rules.Email
import ru.tpu.pretpu.core.base.domain.validation.rules.Max
import ru.tpu.pretpu.core.base.domain.validation.rules.NotEmpty
import ru.tpu.pretpu.core.base.domain.validation.rules.Password

class ValidatorTest {

    @Test
    fun `should return Right when validation is successful`() {
        val form = TestEntity("mail@mail.com", "1234Test", "ru", "akakiy")

        val result = form.validate(form)

        assert(result.isRight)
    }

    @Test
    fun `should return Left when validation failed`() {
        val form = TestEntity("mail@@@mail.com", "1234Test", "ru", "akakiy")

        val result = form.validate(form)

        assert(result.isLeft)
    }

    @Test
    fun `should return correct amount of failures`() {
        val oneErrorForm = TestEntity("mail@@@mail.com", "1234Test", "ru", "akakiy")
        val fourErrorsForm = TestEntity("gssgd", "fsddsv", "", "fgfgfgfgfgfgfgfgfgf21")

        val resultWithOneError = oneErrorForm.validate(oneErrorForm)
        val resultWithFourErrors = fourErrorsForm.validate(fourErrorsForm)

        assert(resultWithOneError.isLeft)
        assert(resultWithFourErrors.isLeft)
        resultWithOneError.onFailure { Assert.assertEquals(1, it.validationFailures.keys.size) }
        resultWithFourErrors.onFailure { Assert.assertEquals(4, it.validationFailures.keys.size) }
    }

    data class TestEntity(
        @Email val email: String,
        @Password val password: String,
        @NotEmpty val language: String,
        @Max(20) val name: String
    ) : Validator()
}