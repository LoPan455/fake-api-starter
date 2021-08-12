package io.tjohander.fakeapistarter

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.metrics.MeterProvider
import io.tjohander.fakeapistarter.model.Post
import io.tjohander.fakeapistarter.service.PostService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class FakeApiStarterApplication(
    @Autowired private val postService: PostService,
    @Autowired private val openTelemetry: OpenTelemetry,
    @Autowired private val meterProvider: MeterProvider
) : CommandLineRunner {

    private val log = LoggerFactory.getLogger(FakeApiStarterApplication::class.java)

    private val tracer = openTelemetry.getTracer("io.tjohander.example")
    private val meter = meterProvider.get("io.tjohander.example")
    private val counter = meter.longCounterBuilder("example_counter").build()
    private val recorder = meter.longValueRecorderBuilder("super_timer").setUnit("ms").build()



    override fun run(vararg args: String?) {
        System.setProperty("otel.resource.attributes", "service.name=OtlpExporterExample")
        val startTime: Long = System.currentTimeMillis()
        val exampleSpan = tracer.spanBuilder("exampleSpan").startSpan()
        log.info("Starting 'run'...")
        exampleSpan.makeCurrent()
        val posts: List<Post>? = postService.getPosts()
        posts?.map {
            exampleSpan.setAttribute("good", "true")
            exampleSpan.setAttribute("Post ID", "${it.id}")
            log.info(it.toString())
        }
        recorder.record(System.currentTimeMillis() - startTime)
        exampleSpan.end()
        Thread.sleep(2000)
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(FakeApiStarterApplication::class.java, *args)
}