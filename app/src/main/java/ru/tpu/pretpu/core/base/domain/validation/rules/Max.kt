package ru.tpu.pretpu.core.base.domain.validation.rules

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class Max(val maxLength: Int) {
}
