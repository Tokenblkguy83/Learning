plugins {
    kotlin("jvm") version "1.9.22" // Ensure this matches your Kotlin version
}

group = "Src"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.slf4j:slf4j-api:2.0.9") // Or your preferred SLF4j version
    implementation("org.slf4j:slf4j-simple:2.0.9") // Or your SLF4j implementation
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3") // For coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.7.3") // For Java 8 integration
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

kotlin {
    jvmToolchain(11) // Or your target JVM version
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes("Main-Class", "Src.MainKt")
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

tasks.register("printJavaVersion") {
    doLast {
        println("Java version: ${System.getProperty("java.version")}")
    }
}
