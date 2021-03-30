package ru.tpu.pretpu.features.news.domain.repository

import ru.tpu.pretpu.core.base.domain.exception.Failure
import ru.tpu.pretpu.core.base.domain.functional.Either
import ru.tpu.pretpu.features.news.domain.entity.Article
import ru.tpu.pretpu.features.news.domain.entity.FeedItem
import ru.tpu.pretpu.features.news.domain.entity.LinkItem
import ru.tpu.pretpu.features.news.domain.entity.MenuParams

interface NewsRepository {
    suspend fun getMainMenu(menuParams: MenuParams): Either<Failure, List<LinkItem>>
    suspend fun getFeedItems(selectedItemId: String): Either<Failure, Pair<String, List<FeedItem>>>
    suspend fun getArticle(articleId: String): Either<Failure, Article>
}