package ru.akinadude.research.presenter

class PostsPresenter

/*
Todo

1) Тут должно быть что-то вроде

marketplaceManager.getTelemedicineUrl(contractInfo.contractId.toString(), contractInfo.serviceId!!)
                .subscribe(this::onReceivedTelemedUrlSuccess, this::onReceivedTelemedUrlError)
                .disposeBy(this)

только вместо subscribe await
И что-то вместо disposeBy

2) Как организовать?
Можно так
fun loadData() = launch {
    view.showLoading()

    val result = withContext(Dispatchers.IO) { dataProvider.loadData() }

    view.showData()
}

Еще варианты?

* */