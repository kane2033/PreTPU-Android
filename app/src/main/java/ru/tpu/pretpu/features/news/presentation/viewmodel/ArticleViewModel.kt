package ru.tpu.pretpu.features.news.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.tpu.pretpu.core.base.presentation.BaseViewModel
import ru.tpu.pretpu.features.news.domain.entity.Article
import ru.tpu.pretpu.features.news.domain.entity.Item
import ru.tpu.pretpu.features.news.domain.interactor.GetArticle
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel
@Inject constructor(private val getArticle: GetArticle) : BaseViewModel() {

    private val _article = MutableLiveData<Article>()
    val article: LiveData<Article> = _article

    fun getArticle(selectedItem: Item, doRefresh: Boolean = false) {
        if (doRefresh || _article.value == null) {
            _isLoading.value = true
            getArticle.invoke(
                params = selectedItem,
                onResult = { it.fold(::handleFailure, ::handleArticleLoaded) },
                job = job
            )
        }
    }

    private fun handleArticleLoaded(article: Article) {
        _isLoading.value = false
        this._article.value = article
    }
}