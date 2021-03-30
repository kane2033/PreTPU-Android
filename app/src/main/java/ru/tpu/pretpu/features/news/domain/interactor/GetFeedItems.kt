package ru.tpu.pretpu.features.news.domain.interactor

import ru.tpu.pretpu.core.base.domain.interactor.UseCase
import ru.tpu.pretpu.features.news.domain.entity.FeedItem
import ru.tpu.pretpu.features.news.domain.repository.NewsRepository
import javax.inject.Inject

/**
 * [UseCase], использующийся получения главного меню (1 уровня):
 * [newsRepository] возвращает список статей
 * */
class GetFeedItems
@Inject constructor(private val newsRepository: NewsRepository) :
    UseCase<Pair<String, List<FeedItem>>, String>() {
    override suspend fun run(params: String) = newsRepository.getFeedItems(params)
}