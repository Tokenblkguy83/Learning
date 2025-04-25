plugins {
    kotlin("jvm") version "1.9.20"
    application
}

group = "com.learning"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    
    // Add logging dependencies
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    
    // Add testing dependencies
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    testImplementation("org.mockito:mockito-core:5.6.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("Src.MainKt")
}

// Configure source sets to match the project structure
sourceSets {
    main {
        kotlin.srcDirs("Src/Main/kotlin")
        resources.srcDirs("Src/Main/Resources")
    }
    test {
        kotlin.srcDirs("Src/Test/kotlin")
        resources.srcDirs("Src/Test/Resources")
    }
}

// Configure Java compatibility
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

// Create a fat JAR with all dependencies
tasks.register<Jar>("uberJar") {
    archiveClassifier.set("uber")
    
    from(sourceSets.main.get().output)
    
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
    
    manifest {
        attributes(mapOf(
            "Main-Class" to application.mainClass.get()
        ))
    }
    
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
