package ru.akinadude.research.mvp.presenter

import ru.akinadude.research.mvp.view.MainView

//todo drawback of docdoc mvp: every time need to create view

class MainPresenter(view: MainView): LifecyclePresenter<MainView>(view) {

    fun navigateToFirstScreen() {
        getView()?.navigateToFirstScreen()
    }

    fun navigateToSecondScreen() {
        getView()?.navigateToSecondScreen()
    }
}