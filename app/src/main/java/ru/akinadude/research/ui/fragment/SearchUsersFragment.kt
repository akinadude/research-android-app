package ru.akinadude.research.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search_users.*
import ru.akinadude.research.R
import ru.akinadude.research.api.GithubApi
import ru.akinadude.research.manager.SearchManager
import ru.akinadude.research.model.github.User
import ru.akinadude.research.mvp.presenter.LifecyclePresenter
import ru.akinadude.research.mvp.presenter.SearchPresenter
import ru.akinadude.research.mvp.view.BaseView
import ru.akinadude.research.mvp.view.SearchUsersView
import ru.akinadude.research.ui.adapter.SearchAdapter

//todo firstly, make without search editText

class SearchUsersFragment : BaseFragment(), SearchUsersView {

    companion object {

        fun newInstance(): SearchUsersFragment {
            return SearchUsersFragment()
        }
    }

    val api = GithubApi()
    val manager = SearchManager(api)
    private val presenter = SearchPresenter(manager, this)

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

    override fun showError(e: Exception) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_search_users, container, false)//todo rename layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_users_recycler_view.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        presenter.showUsers()
    }
}