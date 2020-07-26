plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
}


val scriptUrl: String by extra

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
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("gradle-plugin"))
    implementation("com.squareup:kotlinpoet:1.6.0")
    implementation("org.apache.bcel:bcel:6.0")
    implementation(project(":runtime"))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation(gradleTestKit())
}

tasks {

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
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
