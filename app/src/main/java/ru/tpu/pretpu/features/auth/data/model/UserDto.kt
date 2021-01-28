package ru.tpu.pretpu.features.auth.data.model

data class UserDto(
 val email: String,
 val firstName: String,
 val lastName: String,
 val gender: String,
 val language: String,
 val phoneNumber: String,
 val groupName: String
) {
}