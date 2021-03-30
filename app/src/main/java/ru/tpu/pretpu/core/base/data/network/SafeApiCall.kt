package ru.tpu.pretpu.core.base.data.network

import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import ru.tpu.pretpu.core.base.domain.exception.Failure
import ru.tpu.pretpu.core.base.domain.functional.Either
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Безопасный вызов retrofit запроса с помощью [safeApiResult]
 * с возвратом тела запроса или [Failure].
 * @param [call] - сетевой запрос
 * */
@Singleton
class SafeApiCall
@Inject constructor(private val networkHandler: NetworkHandler) {

    // Получение результата запроса (парам. call)
    suspend fun <T, R> safeApiResult(
        call: suspend () -> Response<T>,
        transform: (T) -> R
    ): Either<Failure, R> {
        if (!networkHandler.isNetworkAvailable()) return Either.Left(Failure.NetworkConnection)
        val response = call.invoke()
        return try {
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Either.Right(transform(responseBody))
                } else {
                    Either.Left(Failure.MissingContentFailure)
                }
            } else {
                Either.Left(
                    Failure.RequestFailure(
                        code = response.code(),
                        message = response.errorBody()?.getField("message")
                    )
                )
            }
            /*when (response.isSuccessful) {
                true -> Either.Right(transform(response.body()!!))
                false -> {
                    Either.Left(
                        Failure.RequestFailure(
                            code = response.code(),
                            message = response.errorBody()?.getField("message")
                        )
                    )
                }
            }*/
        } catch (exception: Throwable) {
            Either.Left(Failure.NetworkConnection)
        }
    }

    // Получение результата запроса с кодом результата (парам. call)
    // (полезно для регистрации, когда возвращается 210)
    suspend fun <T, R> safeApiResultWithCode(
        call: suspend () -> Response<T>,
        transform: (T) -> R
    ): Either<Failure, Pair<Int, R>> {
        if (!networkHandler.isNetworkAvailable()) return Either.Left(Failure.NetworkConnection)
        val response = call.invoke()
        return try {
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Either.Right(
                        Pair(
                            response.code(),
                            transform(responseBody)
                        )
                    )
                } else {
                    Either.Left(Failure.MissingContentFailure)
                }
            } else {
                Either.Left(
                    Failure.RequestFailure(
                        code = response.code(),
                        message = response.errorBody()?.getField("message")
                    )
                )
            }
            /*when (response.isSuccessful) {
                true -> {
                    Either.Right(
                        Pair(
                            response.code(),
                            transform(response.body()!!)
                        )
                    )
                }
                false -> {
                    Either.Left(
                        Failure.RequestFailure(
                            code = response.code(),
                            message = response.errorBody()?.getField("message")
                        )
                    )
                }
            }*/
        } catch (exception: Throwable) {
            Either.Left(Failure.NetworkConnection)
        }
    }

    private fun ResponseBody.getField(field: String): String? {
        val json = this.string()
        return try {
            JSONObject(json).getString(field)
        } catch (e: JSONException) {
            e.printStackTrace()
            null
        }
    }
}