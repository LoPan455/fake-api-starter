import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("org.springframework.boot") version "2.5.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
}

group = "io.tjohander"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // OTLP Dependencies
    implementation("io.opentelemetry:opentelemetry-api:1.4.1")
    implementation("io.opentelemetry:opentelemetry-api:1.4.1")
    implementation("io.opentelemetry:opentelemetry-exporter-otlp:1.4.1")
    implementation("io.opentelemetry:opentelemetry-sdk-extension-autoconfigure:1.4.1-alpha")
    implementation("io.opentelemetry:opentelemetry-api-metrics:1.4.1-alpha")
    implementation("io.opentelemetry:opentelemetry-sdk-metrics:1.4.1-alpha")
    implementation("io.opentelemetry:opentelemetry-exporter-otlp-metrics:1.4.1-alpha")

    implementation ("io.grpc:grpc-netty-shaded:1.39.0")
    implementation ("io.grpc:grpc-protobuf:1.39.0")
    implementation ("io.grpc:grpc-stub:1.39.0")
    compileOnly ("org.apache.tomcat:annotations-api:6.0.53") // necessary for Java 9+


    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
