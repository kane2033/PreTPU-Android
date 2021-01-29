package ru.tpu.pretpu.core.base.data.network.interceptor.refresh.api

import retrofit2.Call
import retrofit2.Retrofit
import ru.tpu.pretpu.core.base.data.network.interceptor.refresh.model.RefreshTokenDto
import javax.inject.Inject

class RefreshTokenApiImpl
@Inject constructor(retrofit: Retrofit) : RefreshTokenApi {

    private val api by lazy { retrofit.create(RefreshTokenApi::class.java) }

    override fun getTokenFromRefresh(refreshToken: String) =
        api.getTokenFromRefresh("Bearer $refreshToken")
}