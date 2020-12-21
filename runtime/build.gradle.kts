plugins {
    kotlin("multiplatform")
    `maven-publish`
}

repositories {
    jcenter()
}

kotlin {
    jvm()
    linuxX64()
    macosX64()
    mingwX64()
}
