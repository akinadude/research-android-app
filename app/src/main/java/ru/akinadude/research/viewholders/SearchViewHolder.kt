package ru.akinadude.research.viewholders

import android.view.View
import kotlinx.android.synthetic.main.item_rv_search_result.view.*
import ru.akinadude.research.R
import ru.akinadude.research.model.github.User
import ru.akinadude.research.utils.PicassoUtils

class SearchViewHolder(itemView: View) : BaseViewHolder<User>(itemView) {

    override fun bind(model: User) {
        itemView.login_text_view.text = model.login
        PicassoUtils.from(itemView)
                .load(model.avatar_url)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_input_black_24dp)
                .into(itemView.avatar_image_view)
    }
}