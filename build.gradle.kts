import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.shadow)
}

group = "de.rechergg"
version = "1.0.0-alpha"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.bundles.mordant)
}

tasks {
    named<ShadowJar>("shadowJar") {
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "de.rechergg.ktimer.KTimerKt"))
        }
    }
}

tasks.jar {
    archiveBaseName.set("ktimer")
    archiveVersion.set("$version")
}