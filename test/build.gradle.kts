
plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.epam.drill.cross-compilation")
//    id("com.epam.drill.kni") version "0.1.0"

}

val drillJvmApiLibVersion: String by extra

repositories {
    mavenLocal()
    maven(url = "https://oss.jfrog.org/artifactory/list/oss-release-local")
    maven(url = "https://dl.bintray.com/kotlin/kotlinx/")
    mavenCentral()
    jcenter()
}

kotlin {
    setOf(
        mingwX64 { binaries.all { linkerOpts("-lpsapi", "-lwsock32", "-lws2_32", "-lmswsock") } },
        linuxX64(),
        macosX64()
    )

    crossCompilation {

        common {
            defaultSourceSet {
                dependsOn(sourceSets.named("commonMain").get())
                dependencies {
                    implementation("com.epam.drill:jvmapi:$drillJvmApiLibVersion")
                }
            }
        }
    }
    jvm("jvm1")
    jvm("jvm2")

//    kni {
//        jvmTargets = sequenceOf(jvm("jvm1"))
//        additionalJavaClasses = sequenceOf(
//            "java.lang.Object",
//            "java.lang.Integer",
//            "java.lang.Double"
//        )
//    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        jvm("jvm1") {
            compilations["main"].defaultSourceSet {
                dependencies {
                    implementation(kotlin("stdlib"))
                }
            }
        }

        jvm("jvm2") {
            compilations["main"].defaultSourceSet {
                dependencies {
                    implementation(kotlin("stdlib"))
                }
            }
        }
    }
}