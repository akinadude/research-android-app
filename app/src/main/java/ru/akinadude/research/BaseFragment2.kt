package ru.akinadude.research

import android.os.Bundle
import android.support.v4.app.Fragment
import ru.akinadude.research.lyfecyclecomponent.UiCoroutineScope

abstract class BaseFragment2 : Fragment() {

    protected val coroutineScope = UiCoroutineScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(coroutineScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(coroutineScope)
    }
}