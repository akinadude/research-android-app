package ru.akinadude.research.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_search_users.*
import kotlinx.coroutines.*
import retrofit2.HttpException
import ru.akinadude.research.R
import ru.akinadude.research.api.GithubApi
import ru.akinadude.research.manager.SearchManager
import ru.akinadude.research.model.github.User
import ru.akinadude.research.mvp.presenter.SearchPresenter
import ru.akinadude.research.mvp.view.SearchUsersView
import ru.akinadude.research.ui.adapter.SearchAdapter
import ru.akinadude.research.utils.BaseTextWatcher


class SearchUsersFragment : Fragment(), SearchUsersView {

    val publishSubject: PublishSubject<String> = PublishSubject.create()

    companion object {

        fun newInstance(): SearchUsersFragment {
            return SearchUsersFragment()
        }
    }

    private val api = GithubApi()
    private val manager = SearchManager(api)
    private val presenter = SearchPresenter(manager, this)
    private val adapter = SearchAdapter()

    override fun showProgress() {
        search_users_progress_bar.visibility = View.VISIBLE
        search_users_recycler_view.visibility = View.GONE
        search_users_error_text_view.visibility = View.GONE
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
            if (t.code() == 403) {
                search_users_error_text_view.text = getString(R.string.search_users_http_403_error)
            } else {
                search_users_error_text_view.text = getString(R.string.search_users_error)
            }
            search_users_error_text_view.visibility = View.VISIBLE
            search_users_progress_bar.visibility = View.GONE
            search_users_recycler_view.visibility = View.GONE
        }
        else -> Unit
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_search_users, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_users_recycler_view.adapter = adapter

        search_query_edit_text.addTextChangedListener(object : BaseTextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //presenter.performSearch(s.toString())
                presenter.performSearchWithPublisher(s.toString(), publishSubject)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
        presenter.subscribeToSubject(publishSubject)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}