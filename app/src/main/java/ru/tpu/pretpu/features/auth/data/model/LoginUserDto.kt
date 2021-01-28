package ru.tpu.pretpu.features.auth.data.model

data class LoginUserDto(
    val token: String,
    val refreshToken: String,
    val user: UserDto
) {
}