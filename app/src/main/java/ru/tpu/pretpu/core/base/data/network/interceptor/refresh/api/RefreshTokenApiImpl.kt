package ru.tpu.pretpu.core.base.data.network.interceptor.refresh.api

import retrofit2.Retrofit
import ru.tpu.pretpu.core.base.di.qualifier.NetworkQualifiers.RetrofitWithoutAuth
import javax.inject.Inject

class RefreshTokenApiImpl
@Inject constructor(@RetrofitWithoutAuth retrofit: Retrofit) : RefreshTokenApi {

    private val api by lazy { retrofit.create(RefreshTokenApi::class.java) }

    override fun getTokenFromRefresh(refreshToken: String) =
        api.getTokenFromRefresh("Bearer $refreshToken")
}