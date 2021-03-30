package ru.tpu.pretpu.features.news.ui.webview

import android.content.Intent
import android.net.Uri
import android.webkit.*

class ArticleWebViewClient(private val onDeepLink: (url: String) -> Unit) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        if (URLUtil.isNetworkUrl(url)) {
            // Если ссылка валидна, открывается в браузере
            view.context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            //startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } else {
            // Если ссылка не валидна
            // пробуем открыть диплинк
            onDeepLink.invoke(url)
            //progressBar.show()
            //openDeepLink(url)
        }
        return true
    }

    // Отключаем скачивание картинки сайта, которой нет (засоряет лог сервиса)
    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest
    ): WebResourceResponse? {
        if (!request.isForMainFrame && request.url.path!!.endsWith("/favicon.ico")) {
            try {
                return WebResourceResponse("image/png", null, null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }
}