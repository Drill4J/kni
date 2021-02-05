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
@file:Suppress("SpellCheckingInspection")

package com.epam.drill.kni.gradle

import com.squareup.kotlinpoet.*
import org.apache.bcel.classfile.*
import org.apache.bcel.generic.*
import org.jetbrains.kotlin.konan.properties.*
import java.io.*
import kotlin.math.*

private const val classRef = "classRef"
private const val objectRef = "objectRef"
private const val kniResult = "kniResult"
private const val selfMethodId = "selfMethodId"

fun JavaClass.isKotlinObject() = fields.any { it.name == "INSTANCE" }

private val jobject = ClassName(japiPack, "jobject")
private val jmethodID = ClassName(japiPack, "jmethodID")
private val jclass = ClassName(japiPack, "jclass")
private val jfieldID = ClassName(japiPack, "jfieldID")

private val withJString = ClassName("com.epam.drill.jvmapi", "withJSting")

class Generator(private val outputDir: File, private val isExperimentalEnabled: Boolean = false) {
    private val methodToSignatureMapping = mutableMapOf<String, String>()
    private val processedClasses = mutableSetOf<String>()

    fun generate(jvmClass: JavaClass, onlyConstructorWrappers: Boolean = false) {
        val className = jvmClass.className.jvmfy()
        val objName = jvmClass.className.replace(jvmClass.packageName + ".", "")

        processedClasses.add(jvmClass.className)

        FileSpec.builder(jvmClass.packageName, objName)
            .addType(
                jvmClass
                    .defineType(objName)
                    .addClassProperty(className)
                    .addSelfMethodProperty(className, jvmClass)
                    .addAnnotation(
                        AnnotationSpec.builder(
                            ClassName("kotlin.native", "ThreadLocal")
                        ).build()
                    )
                    .addMethodFields(jvmClass)
                    .addInitializeBlock(jvmClass)
                    .addMethods(jvmClass.staticMethods(), jvmClass.staticFields())
                    .addFunction(
                        FunSpec
                            .builder("self")
                            .returns(jobject.copy(nullable = true))
                            .receiver(Any::class.asTypeName())
                            .addStatement("return null")
                            .build()
                    )
                    .reinit(jvmClass, objName)
                    .addObjectProperty(jvmClass)
                    .addSelfMethod()
                    .apply {
                        if (!onlyConstructorWrappers)
                            addMethods(jvmClass.nonStaticMethods(), jvmClass.nonStaticFields())
                    }
                    .build()
            )
            .addSimbolsForNativeCall(jvmClass)
            .addImport("kotlin", "Unit")
            .addImport("com.epam.drill.jvmapi", "withJSting")
            .addImport("kotlinx.cinterop", "COpaquePointer")
            .addImport("kotlinx.cinterop", "addressOf")
            .addImport("kotlinx.cinterop", "convert")
            .addImport("kotlinx.cinterop", "usePinned")
            .addImport("kotlinx.cinterop", "toKString")
            .addImport("kotlinx.cinterop", "invoke")
            .addImport("kotlinx.cinterop", "get")
            .addImport("kotlinx.cinterop", "set")
            .addImport(japiPack, "GetStaticObjectField")
            .addImport(japiPack, "NewObject")
            .addImport(japiPack, "NewStringUTF")
            .addImport(japiPack, "FindClass")
            .addImport(japiPack, "GetStaticFieldID")
            .addImport(japiPack, "GetMethodID")
            .addImport(japiPack, "GetStaticMethodID")
            .addImport(japiPack, "CallObjectMethod")
            .addImport(japiPack, "CallStaticObjectMethod")
            .addImport(japiPack, "GetArrayLength")
            .addImport(japiPack, "GetPrimitiveArrayCritical")
            .addImport(japiPack, "ReleasePrimitiveArrayCritical")
            .addImport(japiPack, "JNI_ABORT")
            .apply {
                primitiveMethodMapping.values.forEach {
                    addImport(japiPack, it)
                }
                primitiveStaticMethodMapping.values.forEach {
                    addImport(japiPack, it)
                }
                primitiveReturnTypeMapping.values
                    .filter { it != Unit::class.asTypeName() }
                    .map { it.simpleName }.forEach { arrayType ->
                        addImport(japiPack, "New${arrayType}Array")
                        addImport(japiPack, "Get${arrayType}ArrayElements")
                        addImport(japiPack, "Set${arrayType}ArrayRegion")
                    }

            }

            .addAnnotation(
                AnnotationSpec.builder(Suppress::class)
                    .addMember("\"UnusedImport\"")
                    .addMember("\"RemoveRedundantQualifierName\"")
                    .addMember("\"UNUSED_VARIABLE\"")
                    .addMember("\"unused\"")
                    .addMember("\"UNUSED_PARAMETER\"")
                    .addMember("\"UNNECESSARY_NOT_NULL_ASSERTION\"")
                    .addMember("\"USELESS_CAST\"")
                    .addMember("\"UNNECESSARY_LATEINIT\"")
                    .addMember("\"UNNECESSARY_SAFE_CALL\"")
                    .addMember("\"USELESS_ELVIS\"")
                    .addMember("\"UNCHECKED_CAST\"")
                    .addMember("\"UNUSED_EXPRESSION\"")
                    .build()
            )
            .addImport("kotlinx.cinterop", "invoke")
            .build()
            .writeTo(outputDir)

    }

    private fun FileSpec.Builder.addSimbolsForNativeCall(jvmClass: JavaClass): FileSpec.Builder {
        jvmClass.methods.filter { it.isNative }.forEach {
            val funBuilder = FunSpec.builder(it.name)
            funBuilder
                .addParameter("env", ClassName(japiPack, "JNIEnv"))
                .addParameter("thiz", ClassName(japiPack, "jobject"))
            it.argumentTypes.forEachIndexed { idx, arg ->
                val nullable = !it.isNotNullArg(idx)
                funBuilder.addParameter(
                    it.getArgName(idx),
                    (jniTypeMapping[arg] ?: jobject).copy(nullable)
                )
            }

            addFunction(
                funBuilder
                    .returns((jniTypeMapping[it.returnType] ?: jobject).copy(!it.isNotNullReturnType()))
                    .addStatement(
                        """
     | return %T {   
     |   val rlst =  %T(${
                            it.argumentTypes
                                .mapIndexed { idx, arg -> j2nConvertor(arg, it.getArgName(idx)) }
                                .joinToString(separator = ",")
                        })
     |   ${n2jConverter(it.returnType, "rlst")}
     |}    
 """.trimMargin(),
                        withJString,
                        ClassName(jvmClass.className, it.name)
                    )
                    .addAnnotation(
                        AnnotationSpec
                            .builder(CName)
                            .addMember("\"Java_${jvmClass.className.replace(".", "_")}_${it.name}\"")
                            .build()
                    ).build()
            )
        }
        return this
    }

    private fun Method.isNotNullReturnType() =
        annotationEntries.any { it.annotationType.contains("NotNull") } || returnType is BasicType

    private fun Method.isNotNullArg(idx: Int): Boolean {
        return parameterAnnotationEntries
            .takeIf { it.isNotEmpty<ParameterAnnotationEntry?>() }
            ?.get(idx)
            ?.annotationEntries
            ?.any { it.annotationType.contains("NotNull") } ?: false || argumentTypes[idx] is BasicType
    }

    private fun TypeSpec.Builder.addSelfMethod(): TypeSpec.Builder {
        return addFunction(
            FunSpec
                .builder("self")
                .returns(jobject)
                .addStatement("return $objectRef")
                .build()
        )

    }

    private fun TypeSpec.Builder.reinit(jvmClass: JavaClass, objName: String): TypeSpec.Builder {
        return if (!jvmClass.isKotlinObject()) {
            val paramName = "jb"
            addFunction(
                FunSpec.builder("invoke")
                    .addParameter(paramName, jobject.copy(true))
                    .returns(ClassName(jvmClass.packageName, objName).copy(true))
                    .addModifiers(KModifier.OPERATOR)
                    .addStatement("return if($paramName == null) null else ${jvmClass.packageName}.${objName}($paramName)")
                    .build()
            )

            val classBuilder = TypeSpec.classBuilder(objName).addType(build()).addModifiers(KModifier.OPEN)
            jvmClass.allowedConstructors().onEach {
                val constructorBuilder = FunSpec.constructorBuilder()
                it.argumentTypes.mapIndexed { _, arg ->
                    defineType(arg) ?: return@onEach //filter for non allowed types
                }
                val args = it.argumentTypes.mapIndexed { inx, arg ->
                    val name = it.getArgName(inx)
                    val type = defineType(arg) ?: return@onEach
                    val builder = ParameterSpec
                        .builder(name, if (arg is BasicType) type else type.copy(true))
                    if (arg !is BasicType) {
                        builder.defaultValue("%S", null)
                    }
                    constructorBuilder.addParameter(builder.build())
                    n2jConverter(arg, name, false)
                }.takeIf { it.isNotEmpty() }?.joinToString(prefix = ", ", separator = ", ") ?: ""
                classBuilder.addFunction(
                    constructorBuilder
                        .addStatement("$objectRef = NewObject($classRef, ${it.ref()}$args)!!")
                        .build()
                )

            }
            classBuilder.addFunction(
                FunSpec
                    .constructorBuilder()
                    .addParameter(paramName, jobject)
                    .addStatement("$objectRef = $paramName")
                    .build()
            )
            classBuilder
        } else apply {
            addFunction(
                FunSpec.builder("invoke")
                    .addParameter("ignored", jobject)
                    .returns(ClassName(jvmClass.packageName, objName + "Stub"))
                    .addModifiers(KModifier.OPERATOR)
                    .addStatement("return this")
                    .build()
            )
        }
    }

    private fun n2jConverter(arg: Type?, name: String?, rr: Boolean = false): String? {
        return when (arg) {
            is BasicType -> when (arg) {
                Type.CHAR -> "$name.toShort()" //in jni char === uShort
                Type.BOOLEAN -> "if($name) 1.toUByte() else 0.toUByte()"
                else -> name
            }

            is ObjectType -> when (arg.className) {
                String::class.java.canonicalName -> "NewStringUTF($name)"
                Object::class.java.canonicalName -> "$name as jobject?"
                Class::class.java.canonicalName -> "$name as jclass?"
                else -> {
                    if (isExperimentalEnabled) {
                        if (!processedClasses.contains(arg.className)) {
                            val cls = Class.forName(arg.className)
                            generate(ClassParser(cls.classSource(), cls.name).parse(), rr)
                        }
                        "$name?.self()"
                    } else {
                        "$name as? jobject"
                    }
                }
            }
            is ArrayType -> {
                when (arg.basicType) {
                    is BasicType -> {
                        val arrayType = primitiveReturnTypeMapping[arg.basicType]!!.simpleName
                        """
                            |${name}?.run {
                            |            val jClassBytes = New${arrayType}Array(size)!!
                            |            usePinned { 
                            |                Set${arrayType}ArrayRegion(jClassBytes, 0, size, it.addressOf(0))
                            |            }
                            |            jClassBytes
                            |        }
                        """.trimIndent().trimMargin()
                    }
                    else -> "null"
                }

            }
            else -> name
        }
    }

    private fun TypeSpec.Builder.addMethodFields(jvmClass: JavaClass): TypeSpec.Builder {
        jvmClass.allowedMethods().forEach {
            addProperty(buildMethodRefProperty(it.ref()))
        }
        return this
    }


    private fun TypeSpec.Builder.addInitializeBlock(jvmClass: JavaClass): TypeSpec.Builder {
        return addInitializerBlock(CodeBlock.builder().apply {
            jvmClass.allowedMethods().forEach {
                val methodName = if (!it.isStatic) "GetMethodID" else "GetStaticMethodID"
                addStatement("${it.ref()} = $methodName($classRef, \"${it.name}\", \"${it.signature}\")!!")
            }
        }.build())
    }

    private fun JavaClass.allowedConstructors() = methods.filter { it.name == "<init>" && !it.isPrivate }

    private fun JavaClass.defineType(objName: String): TypeSpec.Builder {
        return if (isKotlinObject()) {
            TypeSpec.objectBuilder(objName + "Stub")
        } else TypeSpec.companionObjectBuilder()
    }

    private fun Method.getArgName(inx: Int) =
        kotlin.runCatching { localVariableTable.getLocalVariable(inx + 1, 0).name }.getOrDefault("arg${inx + 1}")

    val methodForExclude = setOf(
        "<clinit>",
        "equals",
        "hashCode",
        "compareTo",
        "getClass"
    )

    private fun TypeSpec.Builder.addMethods(
        methods: List<Method>,
        fields: List<Field>,
        rr: Boolean = false
    ): TypeSpec.Builder {
        val fieldMapper = fields.associate { "get${it.name.capitalize()}" to it.name }
        methods.forEach {
            val signature = it.signature
            val fieldName = it.ref()
            val returnType = it.returnType
            val funBuilder = FunSpec.builder(it.name)
            val nullable = !it.isNotNullReturnType()
            it.argumentTypes.forEachIndexed { _, arg ->
                defineType(arg) ?: return@forEach //fileter non allowd types
            }

            it.argumentTypes.forEachIndexed { inx, arg ->
                funBuilder.addParameter(it.getArgName(inx), defineType(arg)?.apply {
                    val clsName = this.toString().removeSuffix("?")
                    if (!clsName.startsWith("kotlin") && !processedClasses.contains(clsName)) {
                        val cls = Class.forName(clsName)
                        generate(ClassParser(cls.classSource(), cls.name).parse(), rr)
                    }
                }?.copy(!it.isNotNullArg(inx)) ?: TODO())
            }

            methodToSignatureMapping[signature] = fieldName
            if (fieldMapper.contains(it.name)) {
                addProperty(
                    PropertySpec.builder(fieldMapper[it.name]!!, defineType(returnType)!!)
                        .getter(
                            FunSpec.getterBuilder()
                                .addStatement(buildCallStatement(returnType, it))
                                .addStatement("return ${j2nConvertor(returnType, kniResult, nullable)}")
                                .build()
                        )
                        .build()
                )
            } else {
                defineType(returnType)?.let { rt ->
                    addFunction(
                        funBuilder
                            .apply {
                                if (it.name == "toString" && it.argumentTypes.isEmpty()) {
                                    addModifiers(KModifier.OVERRIDE)
                                }
                            }
                            .returns(rt.copy(nullable))
                            .addStatement(buildCallStatement(returnType, it))
                            .addStatement("return ${j2nConvertor(returnType, kniResult, nullable)}")
                            .build()
                    )
                }
            }
        }
        return this
    }


    private fun buildMethodRefProperty(name: String): PropertySpec {
        return PropertySpec
            .builder(name, jmethodID)
            .mutable()
            .addModifiers(KModifier.PRIVATE, KModifier.LATEINIT)
            .build()
    }

    private fun buildCallStatement(
        returnType: Type?,
        method: Method
    ): String {
        val args = method.argumentTypes.mapIndexed { inx, arg -> n2jConverter(arg, method.getArgName(inx)) }
            .takeIf { it.isNotEmpty() }?.joinToString(",\n", prefix = ",\n") { "          $it" } ?: ""
        val isStatic = method.isStatic
        val jniMethod = defineJniMethod(returnType, isStatic)
        val caller = if (isStatic) classRef else objectRef
        val postProcessor = if (method.isNotNullReturnType()) "!!" else "?: run { return null }"
        val methodId =
            methodToSignatureMapping[method.signature] ?: error("can't map the method ID by ${method.signature}")
        return """
                 |val $kniResult = 
                 |    $jniMethod(
                 |          $caller,
                 |          $methodId
                 |          $args
                 |    )$postProcessor
                 |
                """.trimMargin().trimIndent()
    }

    private fun Class<*>.classSource(): InputStream {
        return getResourceAsStream("/" + canonicalName.jvmfy().suffix("class"))
    }

    private fun j2nConvertor(type: Type?, paramName: String, nullable: Boolean = true): String {
        return when (type) {
            is BasicType -> when (type) {
                Type.BOOLEAN -> "$paramName == 1.toUByte()"
                Type.CHAR -> "$paramName.toShort().toChar()"
                else -> paramName
            }

            is ObjectType -> {
                when (type.className) {
                    String::class.java.canonicalName -> "$paramName?.toKString() ?: \"\""
                    else -> {
                        if (isExperimentalEnabled)
                            "${type.className}($paramName)"
                        else "$paramName as ${Any::class.asTypeName()}${if (nullable) "?" else ""}"
                    }
                }

            }
            is ArrayType -> {
                when (val basicType = type.basicType) {
                    is BasicType -> {
                        val (kt, _) = arrayTypeMapping[basicType] ?: TODO(basicType.toString())

                        val isCharArray = kt.toString() == "kotlin.CharArray"
                        val kArrayName = if (isCharArray) "kotlin.ShortArray" else kt
                        val postProcessing = if (isCharArray)
                            """?.run {
                                    CharArray(length){
                                        this[it].toChar()
                                    } 
                                }
                        """ else ""

                        """ | run {
                            |            val length = GetArrayLength($paramName)
                            |        if (length <= 0) return $kt(length)
                            |        val buffer: COpaquePointer? = GetPrimitiveArrayCritical($paramName, null)
                            |        try {
                            |            $kArrayName(length).apply {
                            |                 usePinned { destination ->
                            |                     platform.posix.memcpy(
                            |                         destination.addressOf(0),
                            |                         buffer,
                            |                         length.convert()
                            |                     )
                            |                 }
                            |             }$postProcessing
                            |        } finally { ReleasePrimitiveArrayCritical($paramName, buffer, JNI_ABORT) }
                            | }
                        """.trimMargin().trimIndent()
                    }
                    else -> TODO(basicType.toString())
                }

            }
            else -> TODO()
        }
    }

    private fun TypeSpec.Builder.addObjectProperty(jvmClass: JavaClass) = addProperty(
        PropertySpec
            .builder(objectRef, jobject)
            .addModifiers(KModifier.PRIVATE)
            .apply {
                if (jvmClass.isKotlinObject()) {
                    getter(
                        FunSpec.getterBuilder()
                            .addStatement("return GetStaticObjectField($classRef, $selfMethodId)!!")
                            .build()
                    )
                }
            }
            .build()
    )

    private fun TypeSpec.Builder.addClassProperty(className: String): TypeSpec.Builder {
        return addProperty(
            PropertySpec
                .builder(classRef, jclass)
                .addModifiers(KModifier.PRIVATE)
                .initializer("FindClass(\"$className\")!!")
                .build()
        )
    }


    private fun TypeSpec.Builder.addSelfMethodProperty(className: String, jvmClass: JavaClass): TypeSpec.Builder {
        return addProperty(
            PropertySpec
                .builder(selfMethodId, jfieldID.copy(nullable = true))
                .apply {
                    if (jvmClass.isKotlinObject()) {
                        initializer("GetStaticFieldID($classRef, \"INSTANCE\", \"L$className;\")")
                    }
                }
                .mutable()
                .build()
        )
    }

    private fun defineJniMethod(type: Type?, isStaic: Boolean): String? {
        return when (type) {
            is BasicType -> if (isStaic) primitiveStaticMethodMapping[type] else primitiveMethodMapping[type]
            is ObjectType, is ArrayType -> if (isStaic) "CallStaticObjectMethod" else "CallObjectMethod"
            else -> TODO("not supported '$type' yet")
        }
    }

    private fun defineType(type: Type?): TypeName? {
        return when (type) {
            is BasicType -> primitiveReturnTypeMapping[type]
            is ObjectType -> calculateObjectType(type)
            is ArrayType -> calculateArrayType(type)
            else -> TODO("not supported '$type' yet")
        }

    }

    private fun calculateArrayType(returnType: ArrayType): TypeName? {
        return arrayTypeMapping[returnType.basicType]?.let { (kotlinArray, _) -> kotlinArray.copy(true) }
    }

    private fun calculateObjectType(returnType: ObjectType): TypeName? {
        return objectReturnTypeMapping[returnType]?.copy(true) ?: run {
            if (isExperimentalEnabled) {
                if (!processedClasses.contains(returnType.className)) {
                    val cls = Class.forName(returnType.className)
                    generate(ClassParser(cls.classSource(), cls.name).parse(), true)
                }
                ClassName(
                    returnType.className.substringBeforeLast("."),
                    returnType.className.substringAfterLast(".")
                ).copy(true)
            } else Any::class.asTypeName().copy(true)
        }
    }

    private fun Method.ref() = "${name.removePrefix("<").removeSuffix(">")}Ref${abs(signature.hashCode())}"

    private fun JavaClass.allowedMethods(): List<Method> {
        return methods
            .filter { !it.isPrivate }
            .filter { !it.isNative }
            .filter { meth -> methodForExclude.none { it == meth.name } }
            .filter { !it.ref().contains("$") }
    }

    private fun JavaClass.staticMethods(): List<Method> {
        return allowedMethods().filter { it.isStatic }
    }

    private fun JavaClass.nonStaticMethods(): List<Method> {
        return allowedMethods().filter { !it.isStatic }
    }

    private fun JavaClass.staticFields(): List<Field> {
        return fields.filter { it.isStatic }
    }

    private fun JavaClass.nonStaticFields(): List<Field> {
        return fields.filter { !it.isStatic }
    }

}

fun String.jvmfy() = replace(".", "/")

