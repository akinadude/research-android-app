package ru.akinadude.research.mvp.presenter

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.akinadude.research.manager.SearchManager
import ru.akinadude.research.model.github.UsersContainer
import ru.akinadude.research.mvp.view.SearchUsersView
import java.util.concurrent.TimeUnit

class SearchPresenter(
        private val manager: SearchManager,
        view: SearchUsersView
) : BaseRxLifecyclePresenter<SearchUsersView>(view) {

    private var disposable: Disposable? = null

    //todo add extension function for methods .subscribeOn(Schedulers.io())
    //                    .observeOn(AndroidSchedulers.mainThread())

    //todo add extension function for method dispose

    fun performSearch(searchText: String) {
        if (searchText.isNotEmpty()) {
            getView()?.showProgress()
            disposable?.dispose()
            disposable = doPerformSearch(searchText)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                getView()?.hideProgress()
                                getView()?.showSearchUsers(it.items)
                            },
                            {
                                getView()?.hideProgress()
                                clear()
                                getView()?.showError(it)
                            }
                    )
        } else {
            disposable?.dispose()
            getView()?.hideProgress()
            clear()
        }
    }

    private fun doPerformSearch(searchText: String): Single<UsersContainer> =
            Single.timer(400, TimeUnit.MILLISECONDS)
                    .flatMap { manager.searchUsers(searchText) }

    private fun clear() {
        getView()?.showSearchUsers(emptyList())
    }
}