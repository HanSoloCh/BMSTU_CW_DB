plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.9.0"  // Serialization plugin
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(libs.kotlinx.coroutines.core)

    // Сериализация
    implementation(libs.ktor.serialization.kotlinx.json)

    // Tests
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(kotlin("test"))
    testImplementation(libs.mockk)
    testImplementation(libs.slf4j.simple)
    testImplementation(libs.logback.classic)
}