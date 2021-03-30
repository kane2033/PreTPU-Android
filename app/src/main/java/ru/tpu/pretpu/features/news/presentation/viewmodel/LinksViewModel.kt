package ru.tpu.pretpu.features.news.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.tpu.pretpu.core.base.presentation.BaseViewModel
import ru.tpu.pretpu.core.base.presentation.Event
import ru.tpu.pretpu.core.base.presentation.ItemClickedInterface
import ru.tpu.pretpu.features.news.domain.entity.LinkItem
import ru.tpu.pretpu.features.news.domain.interactor.GetLinkItems
import javax.inject.Inject

@HiltViewModel
class LinksViewModel
@Inject constructor(private val getLinkItems: GetLinkItems) : BaseViewModel() {

    private val _linkItems = MutableLiveData<List<LinkItem>>()
    val linkItems: LiveData<List<LinkItem>> = _linkItems

    private val _selectedLinkItem = MutableLiveData<Event<LinkItem>>()
    val selectedLinkItem = _selectedLinkItem

    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle = _toolbarTitle

    val onItemClicked = ItemClickedInterface<LinkItem> {
        _selectedLinkItem.value = Event(it)
    }

    fun getLinkItems(parentItem: LinkItem, doRefresh: Boolean = false) {
        if (doRefresh || _linkItems.value == null) {
            getLinkItems.invoke(
                params = parentItem,
                onResult = { it.fold(::handleFailure, ::handleLinkItemLoaded) },
                job = job
            )
        }
    }

    private fun handleLinkItemLoaded(itemsWithTitle: Pair<String, List<LinkItem>>) {
        _linkItems.value = itemsWithTitle.second
        _toolbarTitle.value = itemsWithTitle.first
    }

}