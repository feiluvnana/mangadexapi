plugins {
    kotlin("jvm") version "2.1.0"
    `maven-publish`
}

group = "org.fln"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-jackson:2.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
}

kotlin {
    jvmToolchain(21)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])  // This will publish your Kotlin code
        }
    }
    repositories {
        mavenLocal()  // Publish to the local Maven repository
    }
}