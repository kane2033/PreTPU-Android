package ru.tpu.pretpu.features.auth.data.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.tpu.pretpu.core.credentials.domain.entity.Credentials
import ru.tpu.pretpu.core.credentials.domain.entity.User
import ru.tpu.pretpu.features.auth.data.model.LoginUserDto
import ru.tpu.pretpu.features.auth.domain.entity.LoginUser

interface AuthApi {

    @POST("auth/local/login")
    suspend fun loginAsync(@Body user: LoginUser): Response<LoginUserDto>

    @POST("auth/local/registration")
    suspend fun registerAsync(@Body user: User): Response<Credentials>
}