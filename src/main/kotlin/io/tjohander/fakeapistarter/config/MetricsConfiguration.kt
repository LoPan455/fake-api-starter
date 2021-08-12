package io.tjohander.fakeapistarter.config

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.metrics.MeterProvider
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.autoconfigure.OpenTelemetrySdkAutoConfiguration
import io.opentelemetry.sdk.metrics.SdkMeterProvider
import io.opentelemetry.sdk.metrics.export.IntervalMetricReader
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import java.util.concurrent.TimeUnit

@Configuration
class MetricsConfiguration {

    @Bean
    fun openTelemetrySdk(): OpenTelemetry {
        val spanExporter = OtlpGrpcSpanExporter.builder().setTimeout(2, TimeUnit.SECONDS).build()
        val spanProcessor = BatchSpanProcessor.builder(spanExporter).setScheduleDelay(100, TimeUnit.MILLISECONDS).build()
        val tracerProvider = SdkTracerProvider.builder().addSpanProcessor(spanProcessor).setResource(OpenTelemetrySdkAutoConfiguration.getResource()).build()
        val openTelemetrySdk = OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).buildAndRegisterGlobal()
        Runtime.getRuntime().addShutdownHook(Thread(tracerProvider::close))
        return openTelemetrySdk
    }

    @Bean
    fun meterProvider(): MeterProvider {
        val metricExporter = OtlpGrpcMetricExporter.getDefault()
        val meterProvider = SdkMeterProvider.builder().buildAndRegisterGlobal()
        val intervalMetricReader = IntervalMetricReader
            .builder()
            .setMetricExporter(metricExporter)
            .setMetricProducers(mutableSetOf(meterProvider) as Collection<SdkMeterProvider>)
            .setExportIntervalMillis(1000)
            .buildAndStart()
        Runtime.getRuntime().addShutdownHook(Thread(intervalMetricReader::shutdown))
        return meterProvider
    }


}