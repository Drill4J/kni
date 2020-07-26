plugins {
    kotlin("multiplatform")
    `maven-publish`

}
group = "com.epam.drill.kni"
repositories {
    jcenter()
}

kotlin {
    jvm {
        compilations["main"].defaultSourceSet{
            dependencies{
                implementation(kotlin("stdlib"))
            }
        }
    }
    linuxX64()
    macosX64()
    mingwX64()

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
            }
        }


    }
}
//dependencies {
//    implementation(kotlin("stdlib"))
//}

//val sourcesJar by tasks.registering(Jar::class) {
//    classifier = "sources"
//    from(sourceSets.main.get().allSource)
//}

//publishing {
//    repositories {
//       mavenLocal()
//    }
//    publications {
//        register("mavenJava", MavenPublication::class) {
//            groupId="com.epam.drill.kni"
//            from(components["java"])
//            artifact(sourcesJar.get())
//        }
//    }
//}