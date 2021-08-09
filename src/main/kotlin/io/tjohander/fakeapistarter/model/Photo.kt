package io.tjohander.fakeapistarter.model

data class Photo(
    val albumId: Int = 0,
    val id: Int = 0,
    val title: String? = null,
    val url: String? = null,
    val thumbnailUrl: String? = null,
)
