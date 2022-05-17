
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
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.20.0")
    // Detekt offers a custom rule template at https://github.com/detekt/detekt-custom-rule-template

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

// detekt {
//     toolVersion = "1.20.0"
//     source = files("src/main/kotlin")
//     parallel = true
//     config = files("src/main/resources/detekt-config.yml") // When using a custom config file, the default values are ignored unless you also set the --build-upon-default-config flag.
//     buildUponDefaultConfig = true
//     allRules = false
//     baseline = file("path/to/baseline.xml")
//     disableDefaultRuleSets = false
//     debug = false
//     ignoreFailures = false
//     ignoredBuildTypes = listOf("release")
//     ignoredFlavors = listOf("production")
//     ignoredVariants = listOf("productionRelease")
//     basePath = projectDir.toString()
// }

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }

    // withType<Detekt>().configureEach {
    //     // exclude("**/greeting/**")
    //     jvmTarget = JavaVersion.VERSION_11.toString() // use for type resolution and custom rules
    //     reports {
    //         html.required.set(true) // observe findings in your browser with structure and code snippets
    //         xml.required.set(false) // checkstyle like format mainly for integrations like Jenkins
    //         txt.required.set(false) // similar to the console output, contains issue signature to manually edit baseline files
    //         sarif.required.set(false) // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with Github Code Scanning
    //     }
    // }
    //
    // withType<DetektCreateBaselineTask>().configureEach {
    //     jvmTarget = JavaVersion.VERSION_11.toString() // use for type resolution and custom rules
    // }
    //
    // register<Detekt>("myDetekt") {
    //     description = "Runs a custom detekt build."
    //     setSource(files("src/main/kotlin", "src/test/kotlin"))
    //     config.setFrom(files("$rootDir/config.yml"))
    //     debug = true
    //     reports {
    //         xml {
    //             outputLocation.set(file("build/reports/mydetekt.xml"))
    //         }
    //         html.outputLocation.set(file("build/reports/mydetekt.html"))
    //     }
    //     include("**/*.kt")
    //     include("**/*.kts")
    //     exclude("resources/")
    //     exclude("build/")
    // }
}
