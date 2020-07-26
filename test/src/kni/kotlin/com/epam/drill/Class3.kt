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
import kotlin.ByteArray
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.toKString
import kotlinx.cinterop.usePinned

open class Class3 {
  private var objectRef: jobject

  constructor() {
    objectRef = com.epam.drill.jvmapi.gen.NewObject(classRef, initRef39797)!!
  }

  constructor(jb: jobject) {
    objectRef = jb
  }

  fun self(): jobject = objectRef

  fun method1(): String {
    val methodResult = com.epam.drill.jvmapi.gen.CallObjectMethod(objectRef, method1Ref1942950347)!!
    return com.epam.drill.jvmapi.gen.GetStringUTFChars(methodResult, null)?.toKString() ?: ""
  }

  fun method2(): Int {
    val methodResult = com.epam.drill.jvmapi.gen.CallIntMethod(objectRef, method2Ref39784)!!
    return methodResult
  }

  fun method3(): kotlin.ByteArray {
    val methodResult = com.epam.drill.jvmapi.gen.CallObjectMethod(objectRef, method3Ref1233928)!!
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

  companion object {
    private val classRef: jclass

    var selfMethodId: jfieldID? = null

    private val method1Ref1942950347: jmethodID

    private val method2Ref39784: jmethodID

    private val method3Ref1233928: jmethodID

    private val initRef39797: jmethodID

    init {
      classRef = com.epam.drill.jvmapi.gen.FindClass("com/epam/drill/Class3")!!
      method1Ref1942950347 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "method1",
          "()Ljava/lang/String;")!!
      method2Ref39784 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "method2", "()I")!!
      method3Ref1233928 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "method3", "()[B")!!
      initRef39797 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "<init>", "()V")!!
    }
  }
}
