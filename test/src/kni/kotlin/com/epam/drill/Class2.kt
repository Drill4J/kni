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
import kotlin.Suppress
import kotlin.Unit
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.toKString
import kotlinx.cinterop.usePinned

object Class2 {
  private val classRef: jclass

  var selfMethodId: jfieldID? = null

  private val method1Ref39797: jmethodID

  private val initRef39797: jmethodID

  private var objectRef: jobject

  init {
    classRef = com.epam.drill.jvmapi.gen.FindClass("com/epam/drill/Class2")!!
    selfMethodId = com.epam.drill.jvmapi.gen.GetStaticFieldID(classRef, "INSTANCE",
        "Lcom/epam/drill/Class2;")
    method1Ref39797 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "method1", "()V")!!
    initRef39797 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "<init>", "()V")!!
  }
  init {
    objectRef = com.epam.drill.jvmapi.gen.GetStaticObjectField(classRef, selfMethodId)!!
  }

  operator fun invoke(ignored: jobject): Class2 = this

  fun self(): jobject = objectRef

  fun method1() {
    val methodResult = com.epam.drill.jvmapi.gen.CallVoidMethod(objectRef, method1Ref39797)!!
    return methodResult
  }
}
