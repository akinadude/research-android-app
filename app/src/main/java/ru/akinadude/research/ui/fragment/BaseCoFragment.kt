package ru.akinadude.research.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import ru.akinadude.research.mvp.presenter.LifecyclePresenter
import ru.akinadude.research.mvp.view.BaseView

abstract class BaseCoFragment : Fragment() {

    abstract val lifecyclePresenter: LifecyclePresenter<BaseView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(lifecyclePresenter)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(lifecyclePresenter)
    }
}