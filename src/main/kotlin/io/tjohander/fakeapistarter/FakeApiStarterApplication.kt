package io.tjohander.fakeapistarter

import io.tjohander.fakeapistarter.model.Post
import io.tjohander.fakeapistarter.service.PostService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class FakeApiStarterApplication(
    @Autowired private val postService: PostService
) : CommandLineRunner {

    private val log = LoggerFactory.getLogger(FakeApiStarterApplication::class.java)

    override fun run(vararg args: String?) {
        log.info("Starting 'run'...")
        val posts: List<Post>? = postService.getPosts()
        posts?.map { log.info(it.toString()) }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(FakeApiStarterApplication::class.java, *args)
}