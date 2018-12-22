package ru.akinadude.research.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_search_users.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import retrofit2.HttpException
import ru.akinadude.research.R
import ru.akinadude.research.api.GithubApi
import ru.akinadude.research.manager.SearchManager
import ru.akinadude.research.model.github.User
import ru.akinadude.research.model.github.UsersContainer
import ru.akinadude.research.mvp.presenter.LifecyclePresenter
import ru.akinadude.research.mvp.presenter.SearchPresenter
import ru.akinadude.research.mvp.view.BaseView
import ru.akinadude.research.mvp.view.SearchUsersView
import ru.akinadude.research.ui.adapter.SearchAdapter
import java.util.concurrent.TimeUnit


class SearchUsersFragment : Fragment(), SearchUsersView {

    //todo show text with an error

    //todo implement example with publisher

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

    /*private fun subscribe() {
        //todo разобраться, как работать с паблишерами
        publishSubject = PublishSubject.create()
        disposable = publishSubject.debounce(300, TimeUnit.MILLISECONDS)
                .switchMap { searchText ->
                    manager.searchUsers(searchText)
                            .toObservable()
                            .subscribeOn(Schedulers.io())
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Logger.d("search, onSuccess")
                            adapter.submitList(it.items)
                        },
                        {
                            Logger.d("search, onError, $it")
                            adapter.submitList(emptyList())
                            disposable?.dispose()
                            subscribe()
                        }
                )
    }*/
}