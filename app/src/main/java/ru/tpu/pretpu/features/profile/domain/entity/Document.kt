package ru.tpu.pretpu.features.profile.domain.entity

data class Document(
    val id: String,
    val name: String,
    val loadDate: String,
    val url: String,
    val fileName: String,
    val lastUseDate: String?
) {
}