package ru.tpu.pretpu.core.credentials.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val email: String?,
    val firstName: String?,
    val group: Group?,
    val language: Language?
) : Parcelable