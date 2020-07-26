rootProject.name = "kni"
include(":runtime")
//include(":test")

pluginManagement {
    val kotlinVersion: String by extra
    val drillGradlePluginVersion: String by extra
    plugins {
        kotlin("multiplatform") version kotlinVersion
        id("com.epam.drill.cross-compilation") version drillGradlePluginVersion
    }
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven(url = "http://oss.jfrog.org/oss-release-local")
    }
}