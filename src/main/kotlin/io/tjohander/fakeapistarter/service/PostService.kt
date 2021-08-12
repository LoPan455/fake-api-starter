package io.tjohander.fakeapistarter.service

import io.tjohander.fakeapistarter.model.Post
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class PostService(
    @Autowired private val apiClient: RestTemplate
) {
    private val log = LoggerFactory.getLogger(PostService::class.java)

    fun getPosts(): List<Post>? {
        log.info("Making GET call to downstream API")
        val response = apiClient.exchange(
            "/posts",
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<List<Post>>() {}
        )
        when (response.statusCode) {
            HttpStatus.OK -> log.info("Successfully retrieved data from upstream api")
            else -> log.warn("Non 200 response from downstream: {}", response.statusCode)
        }
        return response.body
    }
}