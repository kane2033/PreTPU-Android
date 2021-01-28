package ru.tpu.pretpu.core.credentials.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Credentials(
    val token: String?,
    val refreshToken: String?,
    val user: User?
) : Parcelable {

}