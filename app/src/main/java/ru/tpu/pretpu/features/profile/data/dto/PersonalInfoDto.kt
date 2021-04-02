package ru.tpu.pretpu.features.profile.data.dto

data class PersonalInfoDto(
    val email: String,
    val firstName: String,
    val lastName: String?,
    val gender: String,
    val groupName: String?,
    val languageId: String,
    val languageName: String,
    val academicPlanUrl: String,
    val scheduleUrl: String,
    val confirmed: Boolean
) {
}