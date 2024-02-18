// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("com.android.library") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.21" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.21" apply false
    id("io.sentry.android.gradle") version "3.5.0"
    id("com.google.devtools.ksp") version "1.9.21-1.0.16" apply false
}

tasks.register("clean") {
    delete(rootProject.buildDir)
}