package ru.tpu.pretpu.features.news.domain.entity

abstract class Item() {
    abstract val id: String
    abstract val position: Int?
    abstract val type: ContentType?
}