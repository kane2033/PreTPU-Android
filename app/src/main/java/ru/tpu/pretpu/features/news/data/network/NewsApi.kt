package ru.tpu.pretpu.features.news.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.tpu.pretpu.features.news.data.model.FeedItemsDto
import ru.tpu.pretpu.features.news.domain.entity.Article
import ru.tpu.pretpu.features.news.domain.entity.LinkItem

interface NewsApi {

    @GET("menu")
    suspend fun getMainMenuAsync(@QueryMap queryParams: Map<String, String>): Response<List<LinkItem>>

    @GET("article/list/")
    suspend fun getFeedItemsAsync(@Query("id") id: String): Response<FeedItemsDto>

    @GET("article")
    suspend fun getArticleAsync(@Query("id") id: String): Response<Article>

}