package ru.tpu.pretpu.features.news.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.tpu.pretpu.core.base.presentation.BaseViewModel
import ru.tpu.pretpu.core.base.presentation.Event
import ru.tpu.pretpu.core.base.presentation.ItemClickedInterface
import ru.tpu.pretpu.features.news.domain.entity.FeedItem
import ru.tpu.pretpu.features.news.domain.interactor.GetFeedItems
import javax.inject.Inject

@HiltViewModel
class FeedViewModel
@Inject constructor(private val getFeedItems: GetFeedItems) : BaseViewModel() {

    private val _feedItems = MutableLiveData<List<FeedItem>>()
    val feedItems: LiveData<List<FeedItem>> = _feedItems

    private val _selectedFeedItem = MutableLiveData<Event<FeedItem>>()
    val selectedFeedItem: LiveData<Event<FeedItem>> = _selectedFeedItem

    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle: LiveData<String> = _toolbarTitle

    val onItemClicked = ItemClickedInterface<FeedItem> {
        _selectedFeedItem.value = Event(it)
    }

    fun getFeedItems(id: String, doRefresh: Boolean = false) {
        if (doRefresh || _feedItems.value == null) {
            _isLoading.value = true
            getFeedItems.invoke(
                params = id,
                onResult = { it.fold(::handleFailure, ::handleFeedItemsLoaded) },
                job = job
            )
        } else {
            Log.d("VIEWMODEL", "Items != null")
        }
    }

    private fun handleFeedItemsLoaded(itemsWithTitle: Pair<String, List<FeedItem>>) {
        _isLoading.value = false
        _toolbarTitle.value = itemsWithTitle.first
        _feedItems.value = itemsWithTitle.second
    }

}