import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    idea
    id("io.gitlab.arturbosch.detekt") version "1.20.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("com.github.ben-manes.versions") version "0.42.0"
}

group = "example.detekt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:${property("springWebVersion")}")

    testImplementation("org.assertj:assertj-core:${property("assertJVersion")}")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${property("springWebVersion")}")
    testImplementation("org.testcontainers:junit-jupiter:${property("testcontainersVersion")}")
}

idea {
    module {
        inheritOutputDirs = false
    }
}

fun setupEnv(task: Test) {
    val env = System.getenv("ENV") ?: "test"
    task.environment("SMOKE_TEST_ENV", env)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }

    withType<Test> {
        useJUnitPlatform {
            excludeTags("smoke")
        }
    }
}
