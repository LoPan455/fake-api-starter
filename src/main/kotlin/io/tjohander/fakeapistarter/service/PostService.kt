package io.tjohander.fakeapistarter.service

import io.tjohander.fakeapistarter.model.Post
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class PostService(
    @Autowired private val apiClient: RestTemplate
) {
    fun getPosts(): List<Post>? = apiClient.exchange(
        "/posts",
        HttpMethod.GET,
        null,
        object : ParameterizedTypeReference<List<Post>> () {}
    ).body
}