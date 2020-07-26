@file:Suppress(
  "UnusedImport",
  "RemoveRedundantQualifierName",
  "UNUSED_VARIABLE",
  "unused",
  "UNUSED_PARAMETER",
  "UNNECESSARY_NOT_NULL_ASSERTION",
  "USELESS_CAST"
)

package com.epam.drill

import com.epam.drill.jvmapi.gen.jclass
import com.epam.drill.jvmapi.gen.jfieldID
import com.epam.drill.jvmapi.gen.jmethodID
import com.epam.drill.jvmapi.gen.jobject
import com.epam.drill.jvmapi.readBytes
import java.lang.Integer
import kotlin.Boolean
import kotlin.ByteArray
import kotlin.Int
import kotlin.IntArray
import kotlin.Short
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.toKString
import kotlinx.cinterop.usePinned

object Class1 {
  private val classRef: jclass

  var selfMethodId: jfieldID? = null

  private val primitiveIntMethodRef39784: jmethodID

  private val objRef1208288229: jmethodID

  private val obj2Ref513081684: jmethodID

  private val clsRef1367277997: jmethodID

  private val primitiveIntMethodRef1263137: jmethodID

  private val primitiveShortMethodRef39794: jmethodID

  private val primitiveBooleanMethodRef39801: jmethodID

  private val stringMethodRef1942950347: jmethodID

  private val byteArrayMethodRef1233928: jmethodID

  private val intArrayMethodRef1233935: jmethodID

  private val methodVoidRef39797: jmethodID

  private val initRef39797: jmethodID

  private var objectRef: jobject

  init {
    classRef = com.epam.drill.jvmapi.gen.FindClass("com/epam/drill/Class1")!!
    selfMethodId = com.epam.drill.jvmapi.gen.GetStaticFieldID(classRef, "INSTANCE",
        "Lcom/epam/drill/Class1;")
    primitiveIntMethodRef39784 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
        "primitiveIntMethod", "()I")!!
    objRef1208288229 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "obj",
        "(Ljava/lang/Integer;)Lcom/epam/drill/Class2;")!!
    obj2Ref513081684 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "obj2",
        "()Lcom/epam/drill/Class4;")!!
    clsRef1367277997 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "cls",
        "(Lcom/epam/drill/Class3;)Ljava/lang/String;")!!
    primitiveIntMethodRef1263137 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
        "primitiveIntMethod", "(I)I")!!
    primitiveShortMethodRef39794 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
        "primitiveShortMethod", "()S")!!
    primitiveBooleanMethodRef39801 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
        "primitiveBooleanMethod", "()Z")!!
    stringMethodRef1942950347 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "stringMethod",
        "()Ljava/lang/String;")!!
    byteArrayMethodRef1233928 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "byteArrayMethod",
        "()[B")!!
    intArrayMethodRef1233935 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "intArrayMethod",
        "()[I")!!
    methodVoidRef39797 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "methodVoid", "()V")!!
    initRef39797 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "<init>", "()V")!!
  }
  init {
    objectRef = com.epam.drill.jvmapi.gen.GetStaticObjectField(classRef, selfMethodId)!!
  }

  operator fun invoke(ignored: jobject): Class1 = this

  fun self(): jobject = objectRef

  fun primitiveIntMethod(): Int {
    val methodResult = com.epam.drill.jvmapi.gen.CallIntMethod(objectRef,
        primitiveIntMethodRef39784)!!
    return methodResult
  }

  fun obj(fls: Integer): Class2 {
    val methodResult = com.epam.drill.jvmapi.gen.CallObjectMethod(objectRef,
        objRef1208288229,fls.self())!!
    return com.epam.drill.Class2(methodResult)
  }

  fun obj2(): Class4 {
    val methodResult = com.epam.drill.jvmapi.gen.CallObjectMethod(objectRef, obj2Ref513081684)!!
    return com.epam.drill.Class4(methodResult)
  }

  fun cls(newClass: Class3): String {
    val methodResult = com.epam.drill.jvmapi.gen.CallObjectMethod(objectRef,
        clsRef1367277997,newClass.self())!!
    return com.epam.drill.jvmapi.gen.GetStringUTFChars(methodResult, null)?.toKString() ?: ""
  }

  fun primitiveIntMethod(intq: Int): Int {
    val methodResult = com.epam.drill.jvmapi.gen.CallIntMethod(objectRef,
        primitiveIntMethodRef1263137,intq)!!
    return methodResult
  }

  fun primitiveShortMethod(): Short {
    val methodResult = com.epam.drill.jvmapi.gen.CallShortMethod(objectRef,
        primitiveShortMethodRef39794)!!
    return methodResult
  }

  fun primitiveBooleanMethod(): Boolean {
    val methodResult = com.epam.drill.jvmapi.gen.CallBooleanMethod(objectRef,
        primitiveBooleanMethodRef39801)!!
    return methodResult == 1.toUByte()
  }

  fun stringMethod(): String {
    val methodResult = com.epam.drill.jvmapi.gen.CallObjectMethod(objectRef,
        stringMethodRef1942950347)!!
    return com.epam.drill.jvmapi.gen.GetStringUTFChars(methodResult, null)?.toKString() ?: ""
  }

  fun byteArrayMethod(): kotlin.ByteArray {
    val methodResult = com.epam.drill.jvmapi.gen.CallObjectMethod(objectRef,
        byteArrayMethodRef1233928)!!
      val jbytes = (methodResult as com.epam.drill.jvmapi.gen.jbyteArray)
                         val length = com.epam.drill.jvmapi.gen.GetArrayLength(jbytes)
                         if(length <= 0) return kotlin.ByteArray(length)
                         val buffer: COpaquePointer? =
            com.epam.drill.jvmapi.gen.GetPrimitiveArrayCritical(jbytes, null)
                                     try {
                                        return kotlin.ByteArray(length).apply {
                                             usePinned { destination ->
                                                 platform.posix.memcpy(
                                                     destination.addressOf(0),
                                                     buffer,
                                                     length.convert()
                                                 )
                                             }
                                         }
                                     } finally {
                                         com.epam.drill.jvmapi.gen.ReleasePrimitiveArrayCritical(
                                             jbytes,
                                             buffer,
                                             com.epam.drill.jvmapi.gen.JNI_ABORT
                                         )
                                     }
                    
  }

  fun intArrayMethod(): kotlin.IntArray {
    val methodResult = com.epam.drill.jvmapi.gen.CallObjectMethod(objectRef,
        intArrayMethodRef1233935)!!
      val jbytes = (methodResult as com.epam.drill.jvmapi.gen.jintArray)
                         val length = com.epam.drill.jvmapi.gen.GetArrayLength(jbytes)
                         if(length <= 0) return kotlin.IntArray(length)
                         val buffer: COpaquePointer? =
            com.epam.drill.jvmapi.gen.GetPrimitiveArrayCritical(jbytes, null)
                                     try {
                                        return kotlin.IntArray(length).apply {
                                             usePinned { destination ->
                                                 platform.posix.memcpy(
                                                     destination.addressOf(0),
                                                     buffer,
                                                     length.convert()
                                                 )
                                             }
                                         }
                                     } finally {
                                         com.epam.drill.jvmapi.gen.ReleasePrimitiveArrayCritical(
                                             jbytes,
                                             buffer,
                                             com.epam.drill.jvmapi.gen.JNI_ABORT
                                         )
                                     }
                    
  }

  fun methodVoid() {
    val methodResult = com.epam.drill.jvmapi.gen.CallVoidMethod(objectRef, methodVoidRef39797)!!
    return methodResult
  }
}
