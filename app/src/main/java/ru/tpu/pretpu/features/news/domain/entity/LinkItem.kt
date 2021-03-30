package ru.tpu.pretpu.features.news.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinkItem(
    override val id: String,
    override val position: Int,
    override val type: ContentType,
    val name: String,
    val url: String?,
    val idArticle: String?,
    val image: String?,
    val children: List<LinkItem>?,
) : Item(), Parcelable {

    // Конструктор для создания айтема при переходе по диплинку
    constructor(contentType: ContentType, id: String, idArticle: String? = null) : this(
        id,
        -1,
        contentType,
        "",
        "",
        idArticle,
        "",
        null
    )
}