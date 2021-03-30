package ru.tpu.pretpu.features.news.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import ru.tpu.pretpu.R
import ru.tpu.pretpu.features.news.domain.entity.ContentType
import ru.tpu.pretpu.features.news.domain.entity.Item
import ru.tpu.pretpu.features.news.domain.entity.LinkItem

object NewsNavigator {

    // Айтем выбран во фрагменте, используем обычный navigate
    fun NavController.navigateByItem(context: Context?, item: Item) {
        navigate(context, item, null)
    }

    // Если айтем выбран из шторки
    fun NavController.navigateByItemSingleTop(context: Context?, item: Item) {
        val options = NavOptions.Builder().setLaunchSingleTop(true).build()
        navigate(context, item, options)
    }

    // Используется при обновлении в linksFragment, чтобы перейти на первый фрагмент
    // с очищением бекстека
    fun NavController.navigateByItemPopStack(context: Context?, item: Item) {
        // Очищаем весь стек
        var isBackStackNotEmpty = popBackStack()
        while (isBackStackNotEmpty) {
            isBackStackNotEmpty = popBackStack()
        }
        navigate(context, item, null)
    }

    /**
     * Навигация в соответствие с переданным [Item], где
     * назначение определяется по полю enum ContentType.
     * @param [options] определяется по вызванному методу ([navigateByItemSingleTop])
     * */
    private fun NavController.navigate(context: Context?, item: Item, options: NavOptions?) {
        when (item.type) {
            // Открытие ссылки в браузере
            ContentType.LINK, ContentType.SCHEDULE -> {
                (item as? LinkItem)?.url?.let {
                    if (URLUtil.isNetworkUrl(it)) {
                        context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                    }
                }
            }
            // Навигация в соответствующий фрагмент приложения
            ContentType.LINKS_LIST -> {
                navigate(R.id.linksFragment, null, options)
            }
            ContentType.FEED_LIST -> {
                navigate(R.id.feedFragment, null, options)
            }
            ContentType.ARTICLE -> {
                navigate(R.id.articleFragment, null, options)
            }
        }
    }
}