package ru.tpu.pretpu.core.base.domain.infrastructure

import android.content.Context
import android.content.ContextWrapper

/**
 * [ConfigChanger] обёртывает изначальный контекст
 * в контекст с настройками пользователя (на данный момент только язык).
 * [updateLocale] должен возвращать новый контекст с измененным языком.
 * */
interface ConfigChanger {

    // return var was ContextWrapper
    fun updateLocale(context: Context, languageCode: String): ContextWrapper
}