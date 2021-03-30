package ru.tpu.pretpu.features.news.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.presentation.BaseListAdapter
import ru.tpu.pretpu.features.news.domain.entity.LinkItem

class LinkItemAdapter :
    BaseListAdapter<LinkItem>(Companion) {

    companion object : DiffUtil.ItemCallback<LinkItem>() {
        override fun areItemsTheSame(oldItem: LinkItem, newItem: LinkItem): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: LinkItem, newItem: LinkItem): Boolean =
            oldItem.id == newItem.id
    }

    override fun getItemViewType(position: Int) = R.layout.item_list
}
