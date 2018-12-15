package ru.akinadude.research.ui.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.akinadude.research.R
import ru.akinadude.research.model.github.User
import ru.akinadude.research.viewholders.SearchViewHolder

class SearchAdapter : ListAdapter<User, SearchViewHolder>(diffCallback) {

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchViewHolder(inflater.inflate(R.layout.item_rv_search_result, parent, false))
    }

    override fun onBindViewHolder(viewHolder: SearchViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }
}