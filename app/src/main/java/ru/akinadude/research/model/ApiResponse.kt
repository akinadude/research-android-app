package ru.akinadude.research.model


data class PostResponse(val posts: List<Post>)

data class UserResponse(val users: List<*/*User*/>)

//todo хорошо бы завести Repository, которому в конструкторе передавать объект Api и объект Db