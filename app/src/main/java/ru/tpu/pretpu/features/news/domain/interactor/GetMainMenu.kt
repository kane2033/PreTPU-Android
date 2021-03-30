package ru.tpu.pretpu.features.news.domain.interactor

import ru.tpu.pretpu.core.base.domain.interactor.None
import ru.tpu.pretpu.core.base.domain.interactor.UseCase
import ru.tpu.pretpu.core.credentials.domain.repository.CredentialsRepository
import ru.tpu.pretpu.features.news.domain.entity.LinkItem
import ru.tpu.pretpu.features.news.domain.entity.MenuParams
import ru.tpu.pretpu.features.news.domain.repository.NewsRepository
import javax.inject.Inject

/**
 * [UseCase], использующийся получения главного меню (1 уровня):
 * [newsRepository] возвращает элементы меню,
 * с помощью [CredentialsRepository] составляем параметры меню
 * */
class GetMainMenu
@Inject constructor(
    private val newsRepository: NewsRepository,
    private val credentialsRepository: CredentialsRepository
) : UseCase<List<LinkItem>, None>() {
    override suspend fun run(params: None) = newsRepository.getMainMenu(
        MenuParams(
            credentialsRepository.languageId ?: "",
            credentialsRepository.email ?: ""
        )
    )
}