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

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asTypeName
import org.apache.bcel.generic.ObjectType
import org.apache.bcel.generic.Type

internal const val japiPack = "com.epam.drill.jvmapi.gen"
internal val CName = ClassName("kotlin.native", "CName")


val jniTypeMapping = mapOf(
    Type.VOID to Unit::class.asTypeName(),
    Type.BOOLEAN to UByte::class.asTypeName(),
    Type.INT to Int::class.asTypeName(),
    Type.SHORT to Short::class.asTypeName(),
    Type.BYTE to Byte::class.asTypeName(),
    Type.LONG to Long::class.asTypeName(),
    Type.DOUBLE to Double::class.asTypeName(),
    Type.FLOAT to Float::class.asTypeName(),
    Type.CHAR to UShort::class.asTypeName(),
    ObjectType(String::class.java.canonicalName) to ClassName(japiPack, "jstring"),
    ObjectType(Thread::class.java.canonicalName) to ClassName(japiPack, "jthread"),
    ObjectType(ThreadGroup::class.java.canonicalName) to ClassName(japiPack, "jthreadGroup"),
    ObjectType(Throwable::class.java.canonicalName) to ClassName(japiPack, "jthrowable")
)

val baseMethodMapping = mapOf(
    Type.VOID to "VoidMethod",
    Type.BOOLEAN to "BooleanMethod",
    Type.INT to "IntMethod",
    Type.SHORT to "ShortMethod",
    Type.BYTE to "ByteMethod",
    Type.LONG to "LongMethod",
    Type.DOUBLE to "DoubleMethod",
    Type.FLOAT to "FloatMethod",
    Type.CHAR to "CharMethod"
)

val primitiveMethodMapping by lazy { baseMethodMapping.mapValues { "Call${it.value}" } }
val primitiveStaticMethodMapping by lazy { baseMethodMapping.mapValues { "CallStatic${it.value}" } }

val primitiveReturnTypeMapping = mapOf(
    Type.BOOLEAN to Boolean::class.asTypeName(),
    Type.INT to Int::class.asTypeName(),
    Type.SHORT to Short::class.asTypeName(),
    Type.BYTE to Byte::class.asTypeName(),
    Type.LONG to Long::class.asTypeName(),
    Type.DOUBLE to Double::class.asTypeName(),
    Type.FLOAT to Float::class.asTypeName(),
    Type.CHAR to Char::class.asTypeName(),
    Type.VOID to Unit::class.asTypeName()
)

val objectReturnTypeMapping = mapOf(
    Type.STRING to String::class.asTypeName(),
    Type.THROWABLE to Throwable::class.asTypeName()
)
val arrayTypeMapping = primitiveReturnTypeMapping.mapValues {
    ClassName("", "${it.value}Array") to ClassName(
        "com.epam.drill.jvmapi.gen",
        "j${it.value.simpleName.toLowerCase()}Array"
    )
}