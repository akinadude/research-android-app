package ru.akinadude.research.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search_users.*
import retrofit2.HttpException
import ru.akinadude.research.R
import ru.akinadude.research.api.GithubApi
import ru.akinadude.research.manager.SearchManager
import ru.akinadude.research.model.github.User
import ru.akinadude.research.mvp.presenter.LifecyclePresenter
import ru.akinadude.research.mvp.presenter.SearchCoPresenter
import ru.akinadude.research.mvp.view.BaseView
import ru.akinadude.research.mvp.view.SearchUsersView
import ru.akinadude.research.ui.adapter.SearchAdapter


class SearchUsersCoFragment : BaseCoFragment(), SearchUsersView {

    companion object {

        fun newInstance(): SearchUsersCoFragment {
            return SearchUsersCoFragment()
        }
    }

    private val api = GithubApi()
    private val manager = SearchManager(api)
    private val presenter = SearchCoPresenter(manager, this)

    private val adapter = SearchAdapter()

    override val lifecyclePresenter: LifecyclePresenter<BaseView>
        get() = presenter


    override fun showProgress() {
        search_users_progress_bar.visibility = View.VISIBLE
        search_users_recycler_view.visibility = View.GONE
    }

    override fun hideProgress() {
        search_users_progress_bar.visibility = View.GONE
        search_users_recycler_view.visibility = View.VISIBLE
    }

    override fun showSearchUsers(users: List<User>) {
        adapter.submitList(users)
    }

    override fun showError(t: Throwable) = when (t) {
        is HttpException -> {
            search_users_progress_bar.visibility = View.GONE
            search_users_recycler_view.visibility = View.GONE
            //error_text_view.visibility = View.VISIBLE
        }
        else -> Unit
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_search_users, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_users_recycler_view.adapter = adapter

        search_query_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.performSearch(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}