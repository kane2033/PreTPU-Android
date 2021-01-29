package ru.tpu.pretpu.core.base.data.network.interceptor.refresh.api

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.PUT
import ru.tpu.pretpu.core.base.data.network.interceptor.refresh.model.RefreshTokenDto

interface RefreshTokenApi {

    @PUT("token")
    fun getTokenFromRefresh(@Header("Authorization") refreshToken: String): Call<RefreshTokenDto>
}