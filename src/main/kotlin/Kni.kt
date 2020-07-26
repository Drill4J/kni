package com.epam.drill.kni.gradle

import com.epam.drill.kni.*
import org.apache.bcel.classfile.*
import org.gradle.api.*
import org.gradle.api.internal.plugins.*
import org.gradle.kotlin.dsl.*
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

            val generateNativeClasses by tasks.registering {
                group = "kni"
                doLast {
                    val config = config()
                    val outputDir = file(config.srcDir)
                    val gen = Generator(outputDir)
                    config.additionalJavaClasses.forEach {
                        val cls = Class.forName(it)
                        gen.generate(ClassParser(cls.getResourceAsStream("/" + it.replace(".", "/").suffix("class")), cls.name).parse())
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
                val compileKotlinTask = mainCompilation.compileKotlinTask
                generateNativeClasses.get().dependsOn(compileKotlinTask)
            }
            targets.nativeTargets().all {
                commonCompilation?.defaultSourceSet {
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
) = withType(KotlinNativeTarget::class).matching(matcher)


fun NamedDomainObjectCollection<KotlinTarget>.jvmTargets(
        matcher: KotlinJvmTarget.() -> Boolean = { true }
) = withType(KotlinJvmTarget::class).matching(matcher)

val KotlinTarget.mainCompilation: KotlinCompilation<*>
    get() = compilations[KotlinCompilation.MAIN_COMPILATION_NAME]

val KotlinNativeTarget.commonCompilation: KotlinNativeCompilation?
    get() = compilations.firstOrNull { it.name == "common" }

open class KniExt(
        private val targets: NamedDomainObjectCollection<KotlinTarget>
) {
    var additionalJavaClasses: Sequence<String> = sequenceOf()
    var jvmTargets: Sequence<KotlinJvmTarget> = sequenceOf()
    var nativeCrossCompileTarget =
            lazy { targets.nativeTargets().first { it.commonCompilation != null }.commonCompilation }
    var srcDir: String = "src/kni/kotlin"
    var jvmtiAgentObjectPath: String? = null

}