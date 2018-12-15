package ru.akinadude.research.model.github

data class UsersContainer(
    val total_count: Long,
    val incomplete_results: Boolean,
    val items: List<User>
)