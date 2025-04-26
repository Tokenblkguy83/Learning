plugins {
    kotlin("jvm") version "1.9.22" // Or the latest Kotlin JVM version you prefer
}

group = "Src"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.slf4j:slf4j-api:2.0.9") // Example for the Logger class
    implementation("org.slf4j:slf4j-simple:2.0.9") // Example for the Logger implementation (for simple console logging)
}

kotlin {
    jvmToolchain(11) // Or the JVM target you prefer
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes("Main-Class", "Src.MainKt") // Assuming your main.kt is in the Src package
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.canRead() }.map { zipTree(it) }
    }) {
        exclude("META-INF/*.SF")
        exclude("META-INF/*.DSA")
        exclude("META-INF/*.RSA")
    }
}

}