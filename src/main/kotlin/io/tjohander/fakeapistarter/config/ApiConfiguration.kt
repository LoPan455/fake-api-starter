package io.tjohander.fakeapistarter.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class ApiConfiguration(
    @Value("\${api.base-url}") private val apiBaseUrl: String
) {
    @Bean
    fun apiClient(restTemplateBuilder: RestTemplateBuilder): RestTemplate {
        return restTemplateBuilder.rootUri(apiBaseUrl).build()
    }
}