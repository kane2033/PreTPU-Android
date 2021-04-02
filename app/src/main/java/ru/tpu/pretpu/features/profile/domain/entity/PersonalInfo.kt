package ru.tpu.pretpu.features.profile.domain.entity

data class PersonalInfo(
    val lastName: String,
    val firstName: String,
    val group: Group
) {
}