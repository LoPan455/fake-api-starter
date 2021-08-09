package io.tjohander.fakeapistarter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FakeApiStarterApplication

fun main(args: Array<String>) {
    runApplication<FakeApiStarterApplication>(*args)
}
