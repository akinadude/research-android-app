package ru.akinadude.research.mvp.presenter

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import ru.akinadude.research.manager.SearchManager
import ru.akinadude.research.model.github.UsersContainer
import ru.akinadude.research.mvp.view.SearchUsersView
import ru.akinadude.research.utils.disposeBy
import ru.akinadude.research.utils.transformSchedulers
import java.util.concurrent.TimeUnit

class SearchPresenter(
        private val manager: SearchManager,
        view: SearchUsersView
) : BaseRxLifecyclePresenter<SearchUsersView>(view) {

    private var disposable: Disposable? = null

    fun performSearch(searchText: String) {
        if (searchText.isNotEmpty()) {
            getView()?.showProgress()
            disposable?.dispose()
            disposable = doPerformSearch(searchText)
                    .transformSchedulers()
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
                    ).disposeBy(this)
        } else {
            disposable?.dispose()
            getView()?.hideProgress()
            clear()
        }
    }

    fun performSearchWithPublisher(searchText: String, subject: PublishSubject<String>) {
        if (searchText.isNotEmpty()) {
            getView()?.showProgress()
            subject.onNext(searchText)
        } else {
            getView()?.hideProgress()
            clear()
        }
    }

    fun subscribeToSubject(subject: PublishSubject<String>) {
        disposable = subject.debounce(300, TimeUnit.MILLISECONDS)
                .switchMap { searchText ->
                    manager.searchUsers(searchText)
                            .toObservable()
                            .subscribeOn(Schedulers.io())
                }
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
                            disposable?.dispose()
                            subscribeToSubject(subject)
                        }
                ).disposeBy(this)
    }

    private fun doPerformSearch(searchText: String): Single<UsersContainer> = Single.timer(400, TimeUnit.MILLISECONDS)
            .flatMap { manager.searchUsers(searchText) }

    private fun clear() {
        getView()?.showSearchUsers(emptyList())
    }
}