package ru.tpu.pretpu.core.credentials.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(
    val id: String?,
    val name: String?,
    val internalGroupId: String?
) : Parcelable {
}