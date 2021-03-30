package ru.tpu.pretpu.features.news.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    override val id: String,
    override val position: Int?,
    override val type: ContentType?,
    val topic: String,
    val text: String?,
    val name: String,
    val createDate: String
) : Item(), Parcelable {
}