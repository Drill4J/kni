package com.epam.drill.kni.gradle

import com.squareup.kotlinpoet.*
import java.io.File

object JvmtiGenerator {

    fun generate(agentPath: String, outputDir: File) {
        FileSpec
                .builder(agentPath.replaceAfterLast(".", ""), "jvmti-utils")
                .addImport("kotlin.native.concurrent", "freeze")
                .addImport("kotlinx.cinterop", "pointed")
                .addImport("kotlinx.cinterop", "ptr")
                .addImport("kotlinx.cinterop", "value")
                .addImport("kotlinx.cinterop", "reinterpret")
                .addImport("kotlinx.cinterop", "alloc")
                .addImport("kotlinx.cinterop", "convert")
                .addImport("kotlinx.cinterop", "invoke")
                .addImport("kotlinx.cinterop", "CPointerVar")
                .addImport("com.epam.drill.jvmapi.gen", "jvmtiEnvVar")
                .addAnnotation(
                        AnnotationSpec.builder(Suppress::class)
                                .addMember("\"unused\"")
                                .addMember("\"UNUSED_PARAMETER\"")
                                .addMember("\"RemoveRedundantQualifierName\"")
                                .build()
                )
                .addFunction(FunSpec
                        .builder("agentOnLoad")
                        .returns(Int::class.asTypeName())
                        .addAnnotation(AnnotationSpec
                                .builder(CName)
                                .addMember("\"Agent_OnLoad\"")
                                .build())
                        .addParameter("vmPointer", ParameterizedTypeName.run {
                            ClassName("kotlinx.cinterop", "CPointer").parameterizedBy(
                                    ClassName("com.epam.drill.jvmapi.gen", "JavaVMVar")
                            )
                        })
                        .addParameter("options", String::class.asTypeName())
                        .addParameter("reservedPtr", Long::class.asTypeName())
                        .addStatement("""
                            return %T {
                                com.epam.drill.jvmapi.vmGlobal.value = vmPointer.freeze()
                                val vm = vmPointer.pointed
                                val jvmtiEnvPtr = alloc<CPointerVar<jvmtiEnvVar>>()
                                vm.value!!.pointed.GetEnv!!(vm.ptr, jvmtiEnvPtr.ptr.reinterpret(), com.epam.drill.jvmapi.gen.JVMTI_VERSION.convert())
                                com.epam.drill.jvmapi.jvmti.value = jvmtiEnvPtr.value
                                jvmtiEnvPtr.value.freeze()
                                $agentPath.agentOnLoad(options)
                            }
                        """.trimIndent(), ClassName("kotlinx.cinterop", "memScoped"))
                        .build()
                )

                .addFunction(FunSpec
                        .builder("agentOnUnload")
                        .addAnnotation(AnnotationSpec
                                .builder(CName)
                                .addMember("\"Agent_OnUnload\"")
                                .build())
                        .addParameter("vmPointer", ParameterizedTypeName.run {
                            ClassName("kotlinx.cinterop", "CPointer").parameterizedBy(
                                    ClassName("com.epam.drill.jvmapi.gen", "JavaVMVar")
                            )
                        })
                        .addStatement("""$agentPath.agentOnUnload()""".trimIndent())
                        .build()
                )
                .addFunction(FunSpec
                        .builder("currentEnvs")
                        .addAnnotation(AnnotationSpec
                                .builder(CName)
                                .addMember("\"currentEnvs\"")
                                .build())
                        .returns(ClassName("com.epam.drill.jvmapi", "JNIEnvPointer"))
                        .addStatement("""return com.epam.drill.jvmapi.currentEnvs()""".trimIndent())
                        .build()
                )

                .addFunction(FunSpec
                        .builder("jvmtii")
                        .addAnnotation(AnnotationSpec
                                .builder(CName)
                                .addMember("\"jvmtii\"")
                                .build())
                        .returns(ParameterizedTypeName.run {
                            ClassName("kotlinx.cinterop", "CPointer").parameterizedBy(
                                    ClassName("com.epam.drill.jvmapi.gen", "jvmtiEnvVar")
                            )
                        }.copy(true))
                        .addStatement(""" return com.epam.drill.jvmapi.jvmtii()""".trimIndent())
                        .build()
                )
                .addFunction(FunSpec
                        .builder("getJvm")
                        .addAnnotation(AnnotationSpec
                                .builder(CName)
                                .addMember("\"getJvm\"")
                                .build())
                        .returns(ParameterizedTypeName.run {
                            ClassName("kotlinx.cinterop", "CPointer").parameterizedBy(
                                    ClassName("com.epam.drill.jvmapi.gen", "JavaVMVar")
                            ).copy(true)
                        })
                        .addStatement(""" return com.epam.drill.jvmapi.vmGlobal.value""".trimIndent())
                        .build()
                )
                .addFunction(FunSpec
                        .builder("checkEx")
                        .addAnnotation(AnnotationSpec
                                .builder(CName)
                                .addMember("\"checkEx\"")
                                .build())
                        .addParameter("errCode", ClassName("com.epam.drill.jvmapi.gen", "jvmtiError"))
                        .addParameter("funName", String::class.asTypeName())

                        .returns(ClassName("com.epam.drill.jvmapi.gen", "jvmtiError"))
                        .addStatement("""return com.epam.drill.jvmapi.checkEx(errCode, funName)""".trimIndent())
                        .build()
                )
                .apply {
                    listOf("JNI_OnUnload", "JNI_GetCreatedJavaVMs", "JNI_CreateJavaVM", "JNI_GetDefaultJavaVMInitArgs").forEach {
                        addFunction(FunSpec
                                .builder(it)
                                .addAnnotation(AnnotationSpec
                                        .builder(CName)
                                        .addMember("\"$it\"")
                                        .build())
                                .addStatement("""// stub""".trimIndent())
                                .build()
                        )
                    }
                }
                .build()
                .writeTo(outputDir)
    }

}
