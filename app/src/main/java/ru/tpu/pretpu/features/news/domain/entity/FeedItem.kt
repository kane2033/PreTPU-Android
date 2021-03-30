package ru.tpu.pretpu.features.news.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedItem(
    override val id: String,
    override val position: Int?,
    override val type: ContentType? = ContentType.ARTICLE,
    val topic: String,
    val briefText: String,
    val name: String,
    val createDate: String,
    val articleImage: String?
) : Item(), Parcelable {
}