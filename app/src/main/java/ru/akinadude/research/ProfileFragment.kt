package ru.akinadude.research

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_coroutines.*
import ru.akinadude.research.api.GithubApi
import ru.akinadude.research.manager.UserManager
import ru.akinadude.research.model.github.User
import ru.akinadude.research.mvp.presenter.UserPresenter
import ru.akinadude.research.mvp.view.UserView

class ProfileFragment : BaseFragment(), UserView {

    companion object {

        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    val api: GithubApi = GithubApi()
    val manager: UserManager = UserManager(api)
    private val presenter: UserPresenter = UserPresenter(manager, this)

    override val lifecyclePresenter
        get() = presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.activity_coroutines, container, false)//todo rename layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        floating_action_button_1.setOnClickListener {
            presenter.showUserInfo("akinadude")
        }
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showUser(user: User) {
        text_view.text = user.name
    }

    override fun showError(e: Exception) {
    }
}