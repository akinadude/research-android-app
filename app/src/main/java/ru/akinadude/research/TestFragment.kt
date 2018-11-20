package ru.akinadude.research

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TestFragment : BaseFragment() {

    //todo remove redundant scope implementations. Scope must be only in LCPesenter

    //todo move all UI into fragments

    //todo read two articles. Write comments into doc.

    //todo Watch the presentation from appsConf. Write noted into doc.

    //todo chaining and parallel network requests.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //todo use UserPresenter here
    }
}