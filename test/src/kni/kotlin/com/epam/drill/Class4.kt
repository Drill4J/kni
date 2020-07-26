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
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.toKString
import kotlinx.cinterop.usePinned

open class Class4 {
  private var objectRef: jobject

  val abs: String
    get() {
      val methodResult = com.epam.drill.jvmapi.gen.CallObjectMethod(objectRef,
          getAbsRef1942950347)!!
      return com.epam.drill.jvmapi.gen.GetStringUTFChars(methodResult, null)?.toKString() ?: ""
    }

  constructor(abs: String? = null) {
    objectRef = com.epam.drill.jvmapi.gen.NewObject(classRef, initRef1428966913, abs)!!
  }

  constructor(jb: jobject) {
    objectRef = jb
  }

  fun self(): jobject = objectRef

  fun method1(): String {
    val methodResult = com.epam.drill.jvmapi.gen.CallObjectMethod(objectRef, method1Ref1942950347)!!
    return com.epam.drill.jvmapi.gen.GetStringUTFChars(methodResult, null)?.toKString() ?: ""
  }

  companion object {
    private val classRef: jclass

    var selfMethodId: jfieldID? = null

    private val method1Ref1942950347: jmethodID

    private val getAbsRef1942950347: jmethodID

    private val initRef1428966913: jmethodID

    init {
      classRef = com.epam.drill.jvmapi.gen.FindClass("com/epam/drill/Class4")!!
      method1Ref1942950347 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "method1",
          "()Ljava/lang/String;")!!
      getAbsRef1942950347 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "getAbs",
          "()Ljava/lang/String;")!!
      initRef1428966913 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "<init>",
          "(Ljava/lang/String;)V")!!
    }
  }
}
