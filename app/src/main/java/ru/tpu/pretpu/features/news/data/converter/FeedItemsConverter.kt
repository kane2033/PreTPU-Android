package ru.tpu.pretpu.features.news.data.converter

import ru.tpu.pretpu.features.news.data.model.FeedItemsDto

fun FeedItemsDto.toFeedListWithTitle() = Pair(title, articles)