package ru.tpu.pretpu.features.news.ui.webview

import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout

// Данная реализация WebChromeClient используется для отображения видео в webview.
class ChromeClient(private val webView: WebView, private val fullScreenContainer: FrameLayout) :
    WebChromeClient() {

    private var fullScreenView: View? = null
    private var fullScreenViewCallback: CustomViewCallback? = null

    override fun onShowCustomView(view: View, customViewCallback: CustomViewCallback) {
        webView.visibility = View.GONE
        fullScreenContainer.visibility = View.VISIBLE
        fullScreenContainer.addView(view)

        fullScreenView = view
        fullScreenViewCallback = customViewCallback
    }

    override fun onHideCustomView() {
        fullScreenContainer.removeView(fullScreenView)
        fullScreenViewCallback?.onCustomViewHidden()
        fullScreenView = null

        webView.visibility = View.VISIBLE
        fullScreenContainer.visibility = View.GONE
    }
}