package ru.akinadude.research.mvp.presenter

import ru.akinadude.research.mvp.view.MainView


class MainPresenter(view: MainView) : LifecyclePresenter<MainView>(view) {

    fun navigateToFirstScreen() {
        getView()?.navigateToFirstScreen()
    }

    fun navigateToSecondScreen() {
        getView()?.navigateToSecondScreen()
    }

    fun navigateToRaceScreen() {
        getView()?.navigateToRaceScreen()
    }
}