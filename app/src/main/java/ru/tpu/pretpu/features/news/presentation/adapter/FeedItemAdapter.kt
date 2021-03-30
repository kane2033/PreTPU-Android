package ru.tpu.pretpu.features.news.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.presentation.BaseListAdapter
import ru.tpu.pretpu.features.news.domain.entity.FeedItem

class FeedItemAdapter :
    BaseListAdapter<FeedItem>(Companion) {

    companion object : DiffUtil.ItemCallback<FeedItem>() {
        override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean =
            oldItem.id == newItem.id
    }

    override fun getItemViewType(position: Int) = R.layout.item_feed
}
