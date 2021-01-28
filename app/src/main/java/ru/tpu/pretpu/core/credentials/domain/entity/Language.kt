package ru.tpu.pretpu.core.credentials.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Language(
    val languageId: String?,
    val languageName: String?
) : Parcelable