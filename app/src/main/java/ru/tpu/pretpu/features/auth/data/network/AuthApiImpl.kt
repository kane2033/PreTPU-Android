package ru.tpu.pretpu.features.auth.data.network

import retrofit2.Retrofit
import ru.tpu.pretpu.core.credentials.domain.entity.User
import ru.tpu.pretpu.features.auth.domain.entity.LoginUser
import javax.inject.Inject

class AuthApiImpl
@Inject constructor(retrofit: Retrofit) : AuthApi {

    private val api by lazy { retrofit.create(AuthApi::class.java) }

    override suspend fun loginAsync(user: LoginUser) = api.loginAsync(user)

    override suspend fun registerAsync(user: User) = api.registerAsync(user)
}