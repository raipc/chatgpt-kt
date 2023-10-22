plugins {
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.serialization") version "1.9.10"
}

group = "io.raipc.chatgpt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("ru.gildor.coroutines:kotlin-coroutines-okhttp:1.0")

    testImplementation(kotlin("test"))
    testImplementation("ch.qos.logback:logback-classic:1.4.11")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}