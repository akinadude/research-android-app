package ru.akinadude.research.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView) {
    open fun onAttached() = Unit

    open fun onDetached() = Unit

    abstract fun bind(model: T)
}