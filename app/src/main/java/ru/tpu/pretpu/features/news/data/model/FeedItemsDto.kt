package ru.tpu.pretpu.features.news.data.model

import ru.tpu.pretpu.features.news.domain.entity.FeedItem

data class FeedItemsDto(val articles: List<FeedItem>, val title: String) {
}