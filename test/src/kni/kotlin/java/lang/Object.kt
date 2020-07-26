@file:Suppress(
  "UnusedImport",
  "RemoveRedundantQualifierName",
  "UNUSED_VARIABLE",
  "unused",
  "UNUSED_PARAMETER",
  "UNNECESSARY_NOT_NULL_ASSERTION",
  "USELESS_CAST"
)

package java.lang

import com.epam.drill.jvmapi.gen.jclass
import com.epam.drill.jvmapi.gen.jfieldID
import com.epam.drill.jvmapi.gen.jmethodID
import com.epam.drill.jvmapi.gen.jobject
import com.epam.drill.jvmapi.readBytes
import kotlin.Int
import kotlin.Long
import kotlin.Suppress
import kotlin.Unit
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.toKString
import kotlinx.cinterop.usePinned

open class Object {
  private var objectRef: jobject

  constructor() {
    objectRef = com.epam.drill.jvmapi.gen.NewObject(classRef, initRef39797)!!
  }

  constructor(jb: jobject) {
    objectRef = jb
  }

  fun self(): jobject = objectRef

  fun clone(): Object {
    val methodResult = com.epam.drill.jvmapi.gen.CallObjectMethod(objectRef, cloneRef2129906595)!!
    return java.lang.Object(methodResult)
  }

  fun notify() {
    val methodResult = com.epam.drill.jvmapi.gen.CallVoidMethod(objectRef, notifyRef39797)!!
    return methodResult
  }

  fun notifyAll() {
    val methodResult = com.epam.drill.jvmapi.gen.CallVoidMethod(objectRef, notifyAllRef39797)!!
    return methodResult
  }

  fun wait(arg0: Long) {
    val methodResult = com.epam.drill.jvmapi.gen.CallVoidMethod(objectRef, waitRef1264111,arg0)!!
    return methodResult
  }

  fun wait(arg0: Long, arg1: Int) {
    val methodResult = com.epam.drill.jvmapi.gen.CallVoidMethod(objectRef,
        waitRef39216884,arg0,arg1)!!
    return methodResult
  }

  fun wait() {
    val methodResult = com.epam.drill.jvmapi.gen.CallVoidMethod(objectRef, waitRef39797)!!
    return methodResult
  }

  fun finalize() {
    val methodResult = com.epam.drill.jvmapi.gen.CallVoidMethod(objectRef, finalizeRef39797)!!
    return methodResult
  }

  companion object {
    private val classRef: jclass

    var selfMethodId: jfieldID? = null

    private val initRef39797: jmethodID

    private val registerNativesRef39797: jmethodID

    private val getClassRef126180830: jmethodID

    private val hashCodeRef39784: jmethodID

    private val equalsRef1565685781: jmethodID

    private val cloneRef2129906595: jmethodID

    private val toStringRef1942950347: jmethodID

    private val notifyRef39797: jmethodID

    private val notifyAllRef39797: jmethodID

    private val waitRef1264111: jmethodID

    private val waitRef39216884: jmethodID

    private val waitRef39797: jmethodID

    private val finalizeRef39797: jmethodID

    init {
      classRef = com.epam.drill.jvmapi.gen.FindClass("java/lang/Object")!!
      initRef39797 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "<init>", "()V")!!
      registerNativesRef39797 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "registerNatives",
          "()V")!!
      getClassRef126180830 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "getClass",
          "()Ljava/lang/Class;")!!
      hashCodeRef39784 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "hashCode", "()I")!!
      equalsRef1565685781 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "equals",
          "(Ljava/lang/Object;)Z")!!
      cloneRef2129906595 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "clone",
          "()Ljava/lang/Object;")!!
      toStringRef1942950347 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "toString",
          "()Ljava/lang/String;")!!
      notifyRef39797 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "notify", "()V")!!
      notifyAllRef39797 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "notifyAll", "()V")!!
      waitRef1264111 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "wait", "(J)V")!!
      waitRef39216884 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "wait", "(JI)V")!!
      waitRef39797 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "wait", "()V")!!
      finalizeRef39797 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "finalize", "()V")!!
    }
  }
}
