plugins {
    kotlin("jvm")
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("plugin.serialization") version "1.9.0"  // Serialization plugin
}

application {
    mainClass.set("com.example.app.ApplicationKt")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.kotlinx.coroutines.core)

    // Для Postgres
    implementation(libs.postgresql)

    // Сериализация
    implementation(libs.ktor.serialization.kotlinx.json)

    // Ktor
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.ktor.server.status.pages)
    implementation(libs.ktor.server.cors) // Для CORS
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.java.jwt)

    // Для Koin
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j) // Logger для Koin
    implementation(libs.logback.classic)

    // Exposed
    implementation(libs.exposed.core)
    // Connection pool
    implementation(libs.hikariCP)
}