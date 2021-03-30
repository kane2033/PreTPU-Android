package ru.tpu.pretpu.features.news.domain.interactor

import ru.tpu.pretpu.core.base.domain.exception.Failure
import ru.tpu.pretpu.core.base.domain.functional.Either
import ru.tpu.pretpu.core.base.domain.interactor.UseCase
import ru.tpu.pretpu.features.news.domain.entity.Article
import ru.tpu.pretpu.features.news.domain.entity.Item
import ru.tpu.pretpu.features.news.domain.entity.LinkItem
import ru.tpu.pretpu.features.news.domain.repository.NewsRepository
import javax.inject.Inject

/**
 * [UseCase], использующийся получения полной статьи из [newsRepository].
 * Если в переданном через параметры пункте id или сам пункт == null,
 * не делаем запрос и возвращаем [Failure.MissingContentFailure].
 * */
class GetArticle
@Inject constructor(private val newsRepository: NewsRepository) :
    UseCase<Article, Item?>() {
    override suspend fun run(params: Item?): Either<Failure, Article> {
        val articleId: String? = params?.let { if (it is LinkItem) it.idArticle else it.id }
        return if (articleId != null) {
            newsRepository.getArticle(articleId)
        } else {
            Either.Left(Failure.MissingContentFailure)
        }
    }
}