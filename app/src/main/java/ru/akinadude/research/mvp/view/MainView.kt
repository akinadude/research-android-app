package ru.akinadude.research.mvp.view

interface MainView : BaseView {
    fun navigateToFirstScreen()
    fun navigateToSecondScreen()
    fun navigateToRaceScreen()
    fun navigateToAsyncScreen()
    fun navigateToThreadsVsCoScreen()
}