rootProject.name = "kni"
include(":runtime")

pluginManagement {
    val kotlinVersion: String by extra
    plugins {
        kotlin("multiplatform") version kotlinVersion
        kotlin("jvm") version kotlinVersion
    }
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven(url = "http://oss.jfrog.org/oss-release-local")
    }
}
