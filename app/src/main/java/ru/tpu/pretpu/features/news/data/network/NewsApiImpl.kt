package ru.tpu.pretpu.features.news.data.network

import retrofit2.Retrofit
import ru.tpu.pretpu.core.base.di.qualifier.NetworkQualifiers.RetrofitWithAuth
import javax.inject.Inject

class NewsApiImpl
@Inject constructor(@RetrofitWithAuth retrofit: Retrofit) : NewsApi {

    private val api by lazy { retrofit.create(NewsApi::class.java) }

    override suspend fun getMainMenuAsync(queryParams: Map<String, String>) =
        api.getMainMenuAsync(queryParams)

    override suspend fun getFeedItemsAsync(id: String) =
        api.getFeedItemsAsync(id)

    override suspend fun getArticleAsync(id: String) = api.getArticleAsync(id)

}