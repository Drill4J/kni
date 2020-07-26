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
import kotlin.Boolean
import kotlin.Byte
import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.Short
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.toKString
import kotlinx.cinterop.usePinned

open class Double {
  private var objectRef: jobject

  constructor(arg0: kotlin.Double? = null) {
    objectRef = com.epam.drill.jvmapi.gen.NewObject(classRef, initRef1258345, arg0)!!
  }

  constructor(arg0: String? = null) {
    objectRef = com.epam.drill.jvmapi.gen.NewObject(classRef, initRef1428966913, arg0)!!
  }

  constructor(jb: jobject) {
    objectRef = jb
  }

  fun self(): jobject = objectRef

  fun isNaN(): Boolean {
    val methodResult = com.epam.drill.jvmapi.gen.CallBooleanMethod(objectRef, isNaNRef39801)!!
    return methodResult == 1.toUByte()
  }

  fun isInfinite(): Boolean {
    val methodResult = com.epam.drill.jvmapi.gen.CallBooleanMethod(objectRef, isInfiniteRef39801)!!
    return methodResult == 1.toUByte()
  }

  fun byteValue(): Byte {
    val methodResult = com.epam.drill.jvmapi.gen.CallByteMethod(objectRef, byteValueRef39777)!!
    return methodResult
  }

  fun shortValue(): Short {
    val methodResult = com.epam.drill.jvmapi.gen.CallShortMethod(objectRef, shortValueRef39794)!!
    return methodResult
  }

  fun intValue(): Int {
    val methodResult = com.epam.drill.jvmapi.gen.CallIntMethod(objectRef, intValueRef39784)!!
    return methodResult
  }

  fun longValue(): Long {
    val methodResult = com.epam.drill.jvmapi.gen.CallLongMethod(objectRef, longValueRef39785)!!
    return methodResult
  }

  fun floatValue(): Float {
    val methodResult = com.epam.drill.jvmapi.gen.CallFloatMethod(objectRef, floatValueRef39781)!!
    return methodResult
  }

  fun doubleValue(): kotlin.Double {
    val methodResult = com.epam.drill.jvmapi.gen.CallDoubleMethod(objectRef, doubleValueRef39779)!!
    return methodResult
  }

  companion object {
    private val classRef: jclass

    var selfMethodId: jfieldID? = null

    private val toStringRef1050091433: jmethodID

    private val toHexStringRef1050091433: jmethodID

    private val valueOfRef946864959: jmethodID

    private val valueOfRef1618330473: jmethodID

    private val parseDoubleRef1428966931: jmethodID

    private val isNaNRef1258349: jmethodID

    private val isInfiniteRef1258349: jmethodID

    private val isFiniteRef1258349: jmethodID

    private val initRef1258345: jmethodID

    private val initRef1428966913: jmethodID

    private val isNaNRef39801: jmethodID

    private val isInfiniteRef39801: jmethodID

    private val toStringRef1942950347: jmethodID

    private val byteValueRef39777: jmethodID

    private val shortValueRef39794: jmethodID

    private val intValueRef39784: jmethodID

    private val longValueRef39785: jmethodID

    private val floatValueRef39781: jmethodID

    private val doubleValueRef39779: jmethodID

    private val hashCodeRef39784: jmethodID

    private val hashCodeRef1258332: jmethodID

    private val equalsRef1565685781: jmethodID

    private val doubleToLongBitsRef1258333: jmethodID

    private val doubleToRawLongBitsRef1258333: jmethodID

    private val longBitsToDoubleRef1264093: jmethodID

    private val compareToRef2045837774: jmethodID

    private val compareRef39033320: jmethodID

    private val sumRef39033315: jmethodID

    private val maxRef39033315: jmethodID

    private val minRef39033315: jmethodID

    private val compareToRef1565685764: jmethodID

    init {
      classRef = com.epam.drill.jvmapi.gen.FindClass("java/lang/Double")!!
      toStringRef1050091433 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "toString",
          "(D)Ljava/lang/String;")!!
      toHexStringRef1050091433 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "toHexString",
          "(D)Ljava/lang/String;")!!
      valueOfRef946864959 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "valueOf",
          "(Ljava/lang/String;)Ljava/lang/Double;")!!
      valueOfRef1618330473 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "valueOf",
          "(D)Ljava/lang/Double;")!!
      parseDoubleRef1428966931 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "parseDouble",
          "(Ljava/lang/String;)D")!!
      isNaNRef1258349 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "isNaN", "(D)Z")!!
      isInfiniteRef1258349 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "isInfinite", "(D)Z")!!
      isFiniteRef1258349 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "isFinite", "(D)Z")!!
      initRef1258345 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "<init>", "(D)V")!!
      initRef1428966913 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "<init>",
          "(Ljava/lang/String;)V")!!
      isNaNRef39801 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "isNaN", "()Z")!!
      isInfiniteRef39801 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "isInfinite", "()Z")!!
      toStringRef1942950347 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "toString",
          "()Ljava/lang/String;")!!
      byteValueRef39777 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "byteValue", "()B")!!
      shortValueRef39794 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "shortValue", "()S")!!
      intValueRef39784 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "intValue", "()I")!!
      longValueRef39785 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "longValue", "()J")!!
      floatValueRef39781 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "floatValue", "()F")!!
      doubleValueRef39779 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "doubleValue", "()D")!!
      hashCodeRef39784 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "hashCode", "()I")!!
      hashCodeRef1258332 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "hashCode", "(D)I")!!
      equalsRef1565685781 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "equals",
          "(Ljava/lang/Object;)Z")!!
      doubleToLongBitsRef1258333 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
          "doubleToLongBits", "(D)J")!!
      doubleToRawLongBitsRef1258333 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
          "doubleToRawLongBits", "(D)J")!!
      longBitsToDoubleRef1264093 = com.epam.drill.jvmapi.gen.GetMethodID(classRef,
          "longBitsToDouble", "(J)D")!!
      compareToRef2045837774 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "compareTo",
          "(Ljava/lang/Double;)I")!!
      compareRef39033320 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "compare", "(DD)I")!!
      sumRef39033315 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "sum", "(DD)D")!!
      maxRef39033315 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "max", "(DD)D")!!
      minRef39033315 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "min", "(DD)D")!!
      compareToRef1565685764 = com.epam.drill.jvmapi.gen.GetMethodID(classRef, "compareTo",
          "(Ljava/lang/Object;)I")!!
    }
  }
}
