rootProject.name = "kni"
include(":runtime")

pluginManagement {
    val kotlinVersion: String by extra
    val licenseVersion: String by extra
    plugins {
        kotlin("multiplatform") version kotlinVersion
        kotlin("jvm") version kotlinVersion
        id("com.github.hierynomus.license") version licenseVersion
    }
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven(url = "http://oss.jfrog.org/oss-release-local")
    }
}
