package ru.tpu.pretpu.core.base.data.network.interceptor.refresh.model

data class RefreshTokenDto(
    val token: String,
    val refreshToken: String
) {
}