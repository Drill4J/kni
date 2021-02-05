/**
 * Copyright 2020 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.epam.drill.kni.gradle

import com.epam.drill.kni.*
import org.apache.bcel.classfile.*
import org.gradle.api.*
import org.gradle.api.internal.plugins.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.targets.jvm.*
import org.jetbrains.kotlin.konan.properties.*
import java.util.jar.*

private const val EXTENSION_NAME = "kni"

val allowedAnnotations = setOf(Kni::class).map { "L${it.java.canonicalName.jvmfy()};" }

@Suppress("unused")
open class KniPlugin : Plugin<Project> {

    lateinit var kotlinExtDsl: DslObject

    override fun apply(target: Project): Unit = target.run {

        pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
            val kotlinExt = extensions.getByName("kotlin") as KotlinProjectExtension
            kotlinExtDsl = DslObject(kotlinExt)

            val generateNativeClasses = project.task("generateNativeClasses") {
                it.group = "kni"
                it.doLast {
                    val config = config()
                    val outputDir = file(config.srcDir)
                    val gen = Generator(outputDir)
                    config.additionalJavaClasses.forEach {
                        val cls = Class.forName(it)
                        gen.generate(
                            ClassParser(
                                cls.getResourceAsStream("/" + it.replace(".", "/").suffix("class")),
                                cls.name
                            ).parse()
                        )
                    }

                    config.jvmtiAgentObjectPath?.let {
                        JvmtiGenerator.generate(it, outputDir)
                    }


                    config.jvmTargets.forEach {
                        val map = it.mainCompilation.compileDependencyFiles
                            .filter { it.extension == "jar" }
                            .map {
                                JarFile(it).use { jar ->
                                    jar.entries().asSequence().toList()
                                        .filter { !it.isDirectory && it.name.contains(".class") }
                                        .map { jar.getInputStream(it).readBytes() }
                                }
                            }.flatten() + it.mainCompilation.compileKotlinTask.outputs.files.singleFile
                            .walkTopDown()
                            .filter { it.isFile && it.extension == "class" }
                            .map { it.inputStream().readBytes() }
                        map.forEach {
                            val jvmClass = ClassParser(it.inputStream(), "").parse()
                            if (jvmClass.annotationEntries.any { allowedAnnotations.contains(it.annotationType) }) {
                                println("Kni class candidate: ${jvmClass.className}")
                                gen.generate(jvmClass)
                            }
                        }
                    }
                }
            }
            @Suppress("UNCHECKED_CAST") val targets =
                kotlinExtDsl.extensions.getByName("targets") as NamedDomainObjectCollection<KotlinTarget>
            targets.jvmTargets().all {
                val compileKotlinTask = it.mainCompilation.compileKotlinTask
                generateNativeClasses.dependsOn(compileKotlinTask)
            }
            targets.nativeTargets().all {
                it.commonCompilation?.defaultSourceSet {
                    kotlin.srcDir(file(config().srcDir).apply { mkdirs() })
                }
            }

            kotlinExtDsl.extensions.create(EXTENSION_NAME, KniExt::class.java, targets)
        }

    }

    private fun config() = kotlinExtDsl.extensions.findByName(EXTENSION_NAME) as KniExt
}

fun NamedDomainObjectCollection<KotlinTarget>.nativeTargets(
    matcher: KotlinNativeTarget.() -> Boolean = { true }
) = withType(KotlinNativeTarget::class.java).matching(matcher)


fun NamedDomainObjectCollection<KotlinTarget>.jvmTargets(
    matcher: KotlinJvmTarget.() -> Boolean = { true }
) = withType(KotlinJvmTarget::class.java).matching(matcher)

val KotlinTarget.mainCompilation: KotlinCompilation<*>
    get() = compilations.getByName(KotlinCompilation.MAIN_COMPILATION_NAME)

val KotlinNativeTarget.commonCompilation: KotlinNativeCompilation?
    get() = compilations.firstOrNull { it.name == "common" }

open class KniExt(
    private val targets: NamedDomainObjectCollection<KotlinTarget>
) {
    var additionalJavaClasses: Sequence<String> = sequenceOf()
    var jvmTargets: Sequence<KotlinJvmTarget> = sequenceOf()
    var nativeCrossCompileTarget = lazy {
        targets.nativeTargets().first { it.commonCompilation != null }.commonCompilation
    }
    var srcDir: String = "src/kni/kotlin"
    var jvmtiAgentObjectPath: String? = null

}