package ru.tpu.pretpu.features.news.data.repository

import ru.tpu.pretpu.core.base.data.network.SafeApiCall
import ru.tpu.pretpu.features.news.data.converter.toFeedListWithTitle
import ru.tpu.pretpu.features.news.data.network.NewsApi
import ru.tpu.pretpu.features.news.domain.entity.MenuParams
import ru.tpu.pretpu.features.news.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl
@Inject constructor(
    private val newsApi: NewsApi,
    private val safeApiCall: SafeApiCall
) : NewsRepository {
    override suspend fun getMainMenu(menuParams: MenuParams) = safeApiCall.safeApiResult({
        newsApi.getMainMenuAsync(
            mapOf(
                "language" to menuParams.languageId,
                "email" to menuParams.email
            )
        )
    }, { it })

    override suspend fun getFeedItems(selectedItemId: String) = safeApiCall.safeApiResult({
        newsApi.getFeedItemsAsync(selectedItemId)
    }, { it.toFeedListWithTitle() })

    override suspend fun getArticle(articleId: String) = safeApiCall.safeApiResult({
        newsApi.getArticleAsync(articleId)
    }, { it })
}