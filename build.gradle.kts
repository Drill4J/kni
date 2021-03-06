import java.net.*

plugins {
    kotlin("jvm")
    id("com.github.hierynomus.license")
    `java-gradle-plugin`
    `maven-publish`
}

val scriptUrl: String by extra
val kotlinVersion: String by extra
val kotlinPoetVersion: String by extra
val bcelVersion: String by extra

allprojects {
    apply(from = "$scriptUrl/git-version.gradle.kts")
    repositories {
        mavenLocal()
        mavenCentral()
        apply(from = "$scriptUrl/maven-repo.gradle.kts")
        jcenter()
    }
}

dependencies {
    implementation(gradleApi())
    implementation(kotlin("gradle-plugin", kotlinVersion))
    implementation("com.squareup:kotlinpoet:$kotlinPoetVersion")
    implementation("org.apache.bcel:bcel:$bcelVersion")
    implementation(project(":runtime"))
    testImplementation(kotlin("test", kotlinVersion))
    testImplementation(kotlin("test-junit", kotlinVersion))
    testImplementation(gradleTestKit())
}

tasks {

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs += "-Xuse-experimental=kotlin.ExperimentalUnsignedTypes"
    }
}

gradlePlugin {
    plugins {
        create("kni") {
            id = "$group.kni"
            implementationClass = "com.epam.drill.kni.gradle.KniPlugin"
        }
    }
}

val licenseFormatSettings by tasks.registering(com.hierynomus.gradle.license.tasks.LicenseFormat::class) {
    source = fileTree(project.projectDir).also {
        include("**/*.kt", "**/*.java", "**/*.groovy")
        exclude("**/.idea")
    }.asFileTree
}

license {
    headerURI = URI("https://raw.githubusercontent.com/Drill4J/drill4j/develop/COPYRIGHT")
}

tasks["licenseFormat"].dependsOn(licenseFormatSettings)
