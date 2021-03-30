package ru.tpu.pretpu.features.news.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.ui.BaseFragment
import ru.tpu.pretpu.databinding.FragmentArticleBinding
import ru.tpu.pretpu.features.news.presentation.viewmodel.ArticleViewModel
import ru.tpu.pretpu.features.news.presentation.viewmodel.MainMenuViewModel
import ru.tpu.pretpu.features.news.ui.webview.ArticleWebViewClient
import ru.tpu.pretpu.features.news.ui.webview.ChromeClient

@AndroidEntryPoint
class ArticleFragment : BaseFragment(R.layout.fragment_article) {

    // Получение viewmodel из NewsNavHostFragment
    private val parentViewModel: MainMenuViewModel by parentViewModels()

    override val viewModel: ArticleViewModel by viewModels()

    private val binding: FragmentArticleBinding by viewBinding(FragmentArticleBinding::bind)

    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var onScrollChangedListener: ViewTreeObserver.OnScrollChangedListener? =
        ViewTreeObserver.OnScrollChangedListener {
            swipeRefreshLayout?.isEnabled = binding.webView.scrollY == 0
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsNavHostFragment = parentFragment?.parentFragment as SwipeRefreshLayoutInterface
        swipeRefreshLayout = newsNavHostFragment.getSwipeRefreshLayout()

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Берем выбранный предмет из viewmodel главного фрагмента
        parentViewModel.selectedItem.value?.peekContent()?.let { item ->
            // Получаем статью
            viewModel.getArticle(item)

            // Коллбэк при свайпе сверху вниз - обновляем статью
            parentViewModel.refreshListener.value = SwipeRefreshLayout.OnRefreshListener {
                viewModel.getArticle(item, true)
            }
        }

        // Синхронизация полей viewmodel данного фрагмента
        // с viewmodel главного фрагмента (главное меню)
        viewModel.isLoading.observe(viewLifecycleOwner, {
            parentViewModel.setIsLoadingValue(it)
        })

        // Настройка WebView
        binding.webView.apply {
            // Возвращаемся на предыдущую ссылку вместо закрытия экрана
            setOnKeyListener(View.OnKeyListener { v: View?, keyCode: Int, event: KeyEvent ->
                if (event.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (this.canGoBack()) {
                            this.goBack()
                            return@OnKeyListener true
                        }
                    }
                }
                false
            })

            // WebViewClient с реализацией диплинков и пр.
            webViewClient = ArticleWebViewClient(parentViewModel.onDeepLinkClicked)

            // Необходимо для открытия видео
            settings.javaScriptEnabled = true
            webChromeClient = ChromeClient(this, binding.fullscreenContainer)
        }
    }

    // Фикс бага, при котором нельзя скроллить вверх,
    // когда достигли самого низа webview и есть swipeRefreshLayout
    override fun onStart() {
        super.onStart()
        swipeRefreshLayout?.viewTreeObserver?.addOnScrollChangedListener(onScrollChangedListener)
    }

    /*
     * Прячем тулбар в onResume и onStop
     * */
    override fun onResume() {
        super.onResume()
        parentViewModel.toggleToolbar(false)
    }

    override fun onStop() {
        super.onStop()
        parentViewModel.toggleToolbar(true)
        swipeRefreshLayout?.viewTreeObserver?.removeOnScrollChangedListener(onScrollChangedListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        swipeRefreshLayout = null
        onScrollChangedListener = null
    }
}