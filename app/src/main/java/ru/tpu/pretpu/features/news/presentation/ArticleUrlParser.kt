package ru.tpu.pretpu.features.news.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.tpu.pretpu.features.news.domain.entity.ContentType
import ru.tpu.pretpu.features.news.domain.entity.Item
import ru.tpu.pretpu.features.news.domain.entity.LinkItem

object ArticleUrlParser {

    // Асинхронный парсинг ссылки
    fun navigateDeepLink(
        url: String,
        drawerItems: List<LinkItem>,
        lifecycleScope: CoroutineScope,
        onLinkParsed: (item: Item?) -> Unit
    ) {
        lifecycleScope.launch {
            val item = withContext(Dispatchers.IO) { getItemFromDeepLink(url, drawerItems) }
            onLinkParsed(item)
        }
    }

    // Получение соответствующего предмета Item в зависимости от ссылки
    // (Item необходим для перехода к определенному фрагменту через класс FragmentReplacer)
    private fun getItemFromDeepLink(url: String, drawerItems: List<LinkItem>): Item? {
        // Разделяем url на части
        val urlArgs = url.split("://", "/")

        // Проверяем префикс
        if (urlArgs[0] != "pretpu") {
            return null
        }

        // Возвращаем item с типом фрагмента, который надо открыть
        val id = urlArgs[2]
        return when (urlArgs[1]) {
            "article" -> {
                LinkItem(ContentType.ARTICLE, id, id)
            }
            "articleList" -> {
                LinkItem(ContentType.FEED_LIST, id)
            }
            "linksList" -> {
                findItemById(id, drawerItems)
            }
            else -> null
        }
    }

    private fun findItemById(id: String, items: List<LinkItem>): LinkItem? {
        return items.asSequence().flatMap { deepFlatten(it).asSequence() }.find { it.id == id }
    }

    // Рекурсивное получение единого списка из children переданного linkItem
    private fun deepFlatten(
        item: LinkItem,
        result: ArrayList<LinkItem> = ArrayList()
    ): List<LinkItem> {
        val children = item.children
        if (children != null) {
            result.add(item)
            for (element in children) {
                deepFlatten(element, result)
            }
        } else {
            result.add(item)
        }
        return result
    }
}