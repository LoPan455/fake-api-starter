package io.tjohander.fakeapistarter.model

data class ToDo(
    val userId: Int = 0,
    val id: Int = 0,
    val title: String? = null,
    val completed: Boolean = false
)
